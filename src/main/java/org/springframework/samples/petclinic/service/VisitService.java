package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VisitService {
	
	private VisitRepository visitRepository;

	@Autowired
	public VisitService(VisitRepository visitRepository) {
		super();
		this.visitRepository = visitRepository;
	}

	@Transactional(readOnly = true)
	public Visit findVisitById(int visitId) {
		return this.visitRepository.findById (visitId);
	}

}
