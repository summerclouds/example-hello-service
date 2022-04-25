package org.summerclouds.example;

import org.springframework.data.repository.CrudRepository;

public interface PageEntryRepository extends CrudRepository<PageEntry, Long> {

	PageEntry findById(long id);
	
}
