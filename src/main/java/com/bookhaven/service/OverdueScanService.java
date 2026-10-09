package com.bookhaven.service;

import com.bookhaven.model.Loan;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/** Periodic background scan demonstrating safe concurrent work next to servlet requests. */
public final class OverdueScanService implements AutoCloseable {
    private static final Logger LOG=Logger.getLogger(OverdueScanService.class.getName());
    private final LibraryService library;
    private final ScheduledExecutorService executor=Executors.newSingleThreadScheduledExecutor(r->{Thread t=new Thread(r,"bookhaven-overdue-scan");t.setDaemon(true);return t;});
    public OverdueScanService(LibraryService library){this.library=library;}
    public void start(){executor.scheduleWithFixedDelay(this::scan,1,60,TimeUnit.SECONDS);}
    private void scan(){try{for(Loan loan:library.overdueLoans())LOG.info(() -> "Overdue loan #"+loan.getId()+": "+loan.getDisplayName()+", due "+loan.getDueOn());}catch(RuntimeException e){LOG.warning("Overdue scan failed: "+e.getMessage());}}
    @Override public void close(){executor.shutdownNow();}
}
