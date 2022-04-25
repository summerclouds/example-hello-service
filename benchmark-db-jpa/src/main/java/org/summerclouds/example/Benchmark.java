package org.summerclouds.example;

import java.net.URL;
import java.util.Date;

import org.summerclouds.common.core.error.ErrorException;
import org.summerclouds.common.core.tool.MCast;
import org.summerclouds.common.core.util.MArgs;
import org.summerclouds.common.core.util.StopWatch;

public class Benchmark {

	private PageEntryRepository repo;
    int cnt;
    int readLoops;

	public Benchmark(MArgs args, PageEntryRepository repository) {
		this.repo = repository;
		cnt = MCast.toint(args.getOption("cnt"), 1000000);
		readLoops = MCast.toint(args.getOption("loops"), 10);
	}

	public void run() throws Exception {
		
		cleanup();

        saveLoad();
        
        read();
        
        delete();
	}

	private void delete() {
		System.out.println(">>> DELETE");
        StopWatch watch = new StopWatch("JPA Delete").start();
        int i = 0;
        for (PageEntry entry : repo.findAll()) {
            if (i % 1000 == 0) {
            	if (i % 50000 == 0) System.out.println();
            	System.out.print(".");
            }
        	repo.delete(entry);
        	i++;
        }
        watch.stop();
        System.out.println();
        System.out.println(watch);
	}

	private void read() {
		System.out.println(">>> READ");
        StopWatch watch = new StopWatch("JPA Read").start();
        for (int i = 0; i < readLoops; i++) {
        	if (i % 50 == 0) System.out.println();
            System.out.print(".");

            for (PageEntry entry : repo.findAll())
                entry.getLinkName();
        }
        watch.stop();
        System.out.println();
        System.out.println(watch);
	}

	private void saveLoad() throws Exception {
		System.out.println(">>> SAVE LOAD");
        StopWatch watch = new StopWatch("JPA SaveLoad").start();
        for (int i = 0; i < cnt; i++) {
            if (i % 1000 == 0) {
            	if (i % 50000 == 0) System.out.println();
            	System.out.print(".");
            }
            PageEntry entry = new PageEntry();
            entry.setLinkName("Entry " + new Date());
            entry.setLinkDestination(new URL("http://www.google.com"));

                repo.save(entry);

            PageEntry loaded =  repo.findById(entry.getId());
            if (!entry.getLinkName().equals(loaded.getLinkName()))
                throw new ErrorException("ADB Not the same");
        }
        watch.stop();
        System.out.println();
        System.out.println(watch);
		
	}

	private void cleanup() {
        System.out.println("Cleanup JPA");
        repo.deleteAll();
	}

}
