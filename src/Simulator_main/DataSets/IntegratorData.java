package Simulator_main.DataSets;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.AdamsBashforthIntegrator;
import org.apache.commons.math3.ode.nonstiff.ClassicalRungeKuttaIntegrator;
import org.apache.commons.math3.ode.nonstiff.DormandPrince853Integrator;
import org.apache.commons.math3.ode.nonstiff.GraggBulirschStoerIntegrator;

import Simulator_main.StopCondition;

public class IntegratorData {
	
	private int targetBody;	
	
	private double maxIntegTime=1;
	private double integTimeStep=1;
	
	private double maxGlobalTime=100;
	
	private double globalTime=0;
	
	private double refElevation=0;
	
	List<StopCondition> IntegStopHandler = new ArrayList<StopCondition>();
	
	private int velocityVectorCoordSystem=0;
	private int degreeOfFreedom=6;
	
	
	double environmentFrequency=1;
	
	int IntegratorType=1;
	
	int AeroDragModel=0;
	int AeroParachuteModel=1;
	double ConstParachuteCd=0.8;
	
	private double groundtrack=0;
	
	double fixedTimestep=0.5;
	double minTimestep=0;
	double maxTimestep=0;
	double relTol =0;
	double absTol =0;
	int nrSteps=10;
	
	private NoiseModel noiseModel = new NoiseModel();
	

	public NoiseModel getNoiseModel() {
		return noiseModel;
	}

	public double getGroundtrack() {
		return groundtrack;
	}

	public void setGroundtrack(double groundtrack) {
		this.groundtrack = groundtrack;
	}

	public double getGlobalTime() {
		return globalTime;
	}


	public void setGlobalTime(double globalTime) {
		this.globalTime = globalTime;
	}

	public int getAeroParachuteModel() {
		return AeroParachuteModel;
	}


	public void setAeroParachuteModel(int aeroParachuteModel) {
		AeroParachuteModel = aeroParachuteModel;
	}


	public double getConstParachuteCd() {
		return ConstParachuteCd;
	}


	public double getMaxGlobalTime() {
		return maxGlobalTime;
	}

	public void setMaxGlobalTime(double maxGlobalTime) {
		this.maxGlobalTime = maxGlobalTime;
	}

	public void setConstParachuteCd(double constParachuteCd) {
		ConstParachuteCd = constParachuteCd;
	}


	FirstOrderIntegrator Integrator;

	public int getIntegratorType() {
		return IntegratorType;
	}


	public int getAeroDragModel() {
		return AeroDragModel;
	}


	public void setAeroDragModel(int aeroDragModel) {
		AeroDragModel = aeroDragModel;
	}


	public void setIntegratorType(int integratorType) {
		IntegratorType = integratorType;
		if (IntegratorType == 1) {
			Integrator = new ClassicalRungeKuttaIntegrator(fixedTimestep);
		} else if (IntegratorType == 0) {
			Integrator = new DormandPrince853Integrator(minTimestep, maxTimestep, absTol, relTol);
		} else if (IntegratorType ==2){
			Integrator = new GraggBulirschStoerIntegrator(minTimestep, maxTimestep, absTol, relTol);
		} else if (IntegratorType == 3){
			Integrator = new AdamsBashforthIntegrator((int) nrSteps, minTimestep, maxTimestep, absTol, relTol);
		} else {
			// Default Value
			System.out.println("Integrator index out of range");
			System.out.println("Fallback to standard setting: DormandPrince853Integrator(1.0e-8, 1.0, 1.0e-10, 1.0e-10)");
			Integrator = new DormandPrince853Integrator(1.0e-8, 1.0, 1.0e-10, 1.0e-10);
		}
	}


	public int getTargetBody() {
		return targetBody;
	}


	public void setTargetBody(int targetBody) {
		this.targetBody = targetBody;
	}


	public FirstOrderIntegrator getIntegrator() {
		return Integrator;
	}

	
	public IntegratorData() {
		super();
	}


	public double getMaxIntegTime() {
		return maxIntegTime;
	}


	public void setMaxIntegTime(double maxIntegTime) {
		this.maxIntegTime = maxIntegTime;
	}


	public double getIntegTimeStep() {
		return integTimeStep;
	}


	public void setIntegTimeStep(double integTimeStep) {
		this.integTimeStep = integTimeStep;
	}


	public double getRefElevation() {
		return refElevation;
	}


	public void setRefElevation(double refElevation) {
		this.refElevation = refElevation;
	}


	public List<StopCondition> getIntegStopHandler() {
		return IntegStopHandler;
	}


	public void setIntegStopHandler(List<StopCondition> integStopHandler) {
		IntegStopHandler = integStopHandler;
	}


	public int getVelocityVectorCoordSystem() {
		return velocityVectorCoordSystem;
	}


	public void setVelocityVectorCoordSystem(int velocityVectorCoordSystem) {
		this.velocityVectorCoordSystem = velocityVectorCoordSystem;
	}


	public int getDegreeOfFreedom() {
		return degreeOfFreedom;
	}


	public void setDegreeOfFreedom(int degreeOfFreedom) {
		this.degreeOfFreedom = degreeOfFreedom;
	}

	public double getEnvironmentFrequency() {
		return environmentFrequency;
	}

	public void setEnvironmentFrequency(double frequency) {
		environmentFrequency = frequency;
	}

	public double getFixedTimestep() {
		return fixedTimestep;
	}

	public void setFixedTimestep(double fixedTimestep) {
		this.fixedTimestep = fixedTimestep;
	}

	public double getMinTimestep() {
		return minTimestep;
	}

	public void setMinTimestep(double minTimestep) {
		this.minTimestep = minTimestep;
	}

	public double getMaxTimestep() {
		return maxTimestep;
	}

	public void setMaxTimestep(double maxTimestep) {
		this.maxTimestep = maxTimestep;
	}

	public double getRelTol() {
		return relTol;
	}

	public void setRelTol(double relTol) {
		this.relTol = relTol;
	}

	public double getAbsTol() {
		return absTol;
	}

	public void setAbsTol(double absTol) {
		this.absTol = absTol;
	}

	public int getNrSteps() {
		return nrSteps;
	}

	public void setNrSteps(int nrSteps) {
		this.nrSteps = nrSteps;
	}
	
}
