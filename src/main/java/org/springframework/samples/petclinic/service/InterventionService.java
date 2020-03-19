package org.springframework.samples.petclinic.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Intervention;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.InterventionRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.stereotype.Service;

@Service
public class InterventionService {
	
	private InterventionRepository interventionRepository;
	private VisitRepository visitRepository;


	@Autowired
	public InterventionService(InterventionRepository interventionRepository, VisitRepository visitRepository) {
		this.interventionRepository = interventionRepository;
		this.visitRepository = visitRepository;
	}

	public void saveIntervention(@Valid Intervention intervention) {
		this.interventionRepository.save(intervention);
	}

	public Intervention findInterventionById(int interventionId) {
		return this.interventionRepository.findInterventionById(interventionId);
	}

	public void deleteIntervention(Intervention intervention) {
		Visit visit = intervention.getVisit();
		visit.setIntervention(null);
		this.visitRepository.save(visit);
		this.interventionRepository.delete(intervention);
	}
	
	
	
	


}
