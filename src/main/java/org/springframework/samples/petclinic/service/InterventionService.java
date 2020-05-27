package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Intervention;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.InterventionRepository;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.stereotype.Service;

@Service
public class InterventionService {
	
	private InterventionRepository interventionRepository;
	private VisitRepository visitRepository;
	private VetRepository vetRepository;


	@Autowired
	public InterventionService(InterventionRepository interventionRepository, VisitRepository visitRepository, VetRepository vetRepository) {
		this.interventionRepository = interventionRepository;
		this.visitRepository = visitRepository;
		this.vetRepository = vetRepository;

	}

	public void saveIntervention(@Valid Intervention intervention) {
		this.interventionRepository.save(intervention);
	}

	public Intervention findInterventionById(int interventionId) {
		return this.interventionRepository.findInterventionById(interventionId);
	}
	
	//Profiling
	public Intervention findInterventionWithVisitById (int interventionId) {
		return this.interventionRepository.findInterventionWithVisitById (interventionId);
	}
	
	public Intervention findInterventionWithProductsById (int interventionId) {
		return this.interventionRepository.findInterventionWithProductsById (interventionId);
	}
	
	public Intervention findInterventionWithVisitAndProductsById (int interventionId) {
		return this.interventionRepository.findInterventionWithVisitAndProductsById (interventionId);
	}

	public void deleteIntervention(Intervention intervention) {
		Visit visit = intervention.getVisit();
		visit.setIntervention(null);
		this.visitRepository.save(visit);
		this.interventionRepository.delete(intervention);
	}

	public Collection<Intervention> getInterventionsOfDay(LocalDate date) {
		return this.interventionRepository.getInterventionsOfDay (date);
	}
	
	public Collection<Vet> getAvailableVets(LocalDate date) {

		Collection<Intervention> interventionsOfDay = this.getInterventionsOfDay(date);
		
		Collection<Vet> allVets = this.vetRepository.findAll();

		Collection<Vet> availableVets = new ArrayList<Vet>();
		
		Map <Vet, List <Intervention>> allInterventionsTodayByVet = new HashMap<Vet, List<Intervention>>();
		
		allVets.stream().forEach(v -> allInterventionsTodayByVet.put(v, new ArrayList <Intervention> ()));

		Map <Vet, List <Intervention>> interventionsTodayByVet = interventionsOfDay.stream().collect(Collectors.groupingBy(Intervention::getVet));
		
		for (Vet v:interventionsTodayByVet.keySet()) allInterventionsTodayByVet.replace(v, interventionsTodayByVet.get(v));

		availableVets.addAll(allVets.stream().filter(v -> ((List<Intervention>) allInterventionsTodayByVet.get(v)).size() < 3)
				.collect(Collectors.toList()));

		return availableVets;

	}

}
