package org.springframework.samples.petclinic.projections;

import java.time.LocalDate;

public interface VisitIntervention {
	
	LocalDate getVisitDate ();
	String getVisitDescription ();
	String getVisitBringer ();
	
	Integer getVisitId ();
	
	String getInterventionName ();
	String getInterventionFirstName ();
	String getInterventionLastName ();
	
	Integer getInterventionId ();
	

}
