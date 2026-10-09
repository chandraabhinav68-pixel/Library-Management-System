package com.bookhaven;

import com.bookhaven.config.Database;
import com.bookhaven.dao.*;
import com.bookhaven.service.*;
import com.bookhaven.web.ApiServlet;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import java.io.File;
import java.util.logging.Logger;

/** Starts the embedded Tomcat server and wires the application's layers. */
public final class LibraryApplication {
    private static final Logger LOG=Logger.getLogger(LibraryApplication.class.getName());
    private LibraryApplication() { }
    public static void main(String[] args)throws Exception{
        Database.initialize();
        var library=new LibraryService(new JdbcBookRepository(),new JdbcMemberRepository(),new JdbcLoanRepository());
        ApiServlet.library=library;
        var overdueScan=new OverdueScanService(library);
        Tomcat tomcat=new Tomcat();tomcat.setPort(Integer.getInteger("port",8080));tomcat.getConnector();
        File webRoot=new File("src/main/webapp").getAbsoluteFile();Context context=tomcat.addContext("",webRoot.getPath());
        context.setParentClassLoader(LibraryApplication.class.getClassLoader());
        context.addWelcomeFile("index.html");
        context.addMimeMapping("html","text/html;charset=UTF-8");
        context.addMimeMapping("css","text/css;charset=UTF-8");
        context.addMimeMapping("js","application/javascript;charset=UTF-8");
        map(context,"static-files",org.apache.catalina.servlets.DefaultServlet.class,"/");
        map(context,"dashboard",com.bookhaven.web.DashboardServlet.class,"/api/dashboard");
        map(context,"books",com.bookhaven.web.BooksServlet.class,"/api/books");
        map(context,"members",com.bookhaven.web.MembersServlet.class,"/api/members");
        map(context,"loans",com.bookhaven.web.LoansServlet.class,"/api/loans");
        map(context,"loan-actions",com.bookhaven.web.LoansServlet.class,"/api/loans/*");
        tomcat.getServer().addLifecycleListener(event->{if("stop".equals(event.getType()))overdueScan.close();});
        tomcat.start();overdueScan.start();LOG.info("Book Haven is running at http://localhost:"+tomcat.getConnector().getLocalPort());tomcat.getServer().await();
    }
    private static void map(Context context,String name,Class<? extends jakarta.servlet.Servlet> type,String path){
        var wrapper=Tomcat.addServlet(context,name,type.getName());
        wrapper.setLoadOnStartup(1);
        context.addServletMappingDecoded(path,name);
    }
}
