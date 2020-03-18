package org.springframework.samples.petclinic.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Intervention;
import org.springframework.samples.petclinic.repository.InterventionRepository;
import org.springframework.stereotype.Service;

@Service
public class InterventionService {
	
	private InterventionRepository interventionRepository;

	@Autowired
	public InterventionService(InterventionRepository interventionRepository) {
		this.interventionRepository = interventionRepository;
	}

	public void saveIntervention(@Valid Intervention intervention) {
		this.interventionRepository.save(intervention);
	}

	public Intervention findInterventionById(int interventionId) {
		return this.interventionRepository.findInterventionById(interventionId);
	}
	
	
	
	


}
