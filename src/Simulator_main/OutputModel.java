package Simulator_main;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

import FlightElement.SpaceShip;
import FlightElement.ForceTorqueModel.ActuatorSet;
import FlightElement.GNCModel.ControlCommandSet;
import FlightElement.SensorModel.SensorSet;
import Simulation.Model.DataSets.AerodynamicSet;
import Simulation.Model.DataSets.AtmosphereSet;
import Simulation.Model.DataSets.ForceMomentumSet;
import Simulation.Model.DataSets.GravitySet;
import Simulation.Model.DataSets.MasterSet;
import Simulator_main.DataSets.IntegratorData;
import Simulator_main.DataSets.RealTimeContainer;
import Simulator_main.DataSets.RealTimeResultSet;
import utils.Quaternion;
import utils.Plotter.DataContainer;
import utils.Plotter.DataSetXY;
import utils.Plotter.Pair;
import utils.Plotter.PlotXY;

public class OutputModel {
	
	private String RESULT_FILE_NAME  = "results";
	private String RESULT_FILE_ENDING = ".csv";
	
     double PI    = 3.141592653589793238462643383279;   // PI       [-] 
	 double deg2rad 	   = PI/180.0; 					    		 //Convert degrees to radians
	 double rad2deg 	   = 180.0/PI; 					    		 //Convert radians to degrees
	
	@SuppressWarnings("unused")
	private boolean isPlot;
	
	@SuppressWarnings("unused")
	private  DataContainer dataContainer = new DataContainer();
	@SuppressWarnings("unused")
	private  DataSetXY dataSet =  new DataSetXY();
	
	public OutputModel(boolean isPlot) {
		this.isPlot = isPlot;
		
	}
	
public   void createWriteOut(ArrayList<String> steps) {
	if(isPlot) {
	dataContainer.addDataSet(dataSet);
	PlotXY plot = new PlotXY();
	plot.plot(dataContainer);
	}
    try{
        String resultpath="";
        	String dir = System.getProperty("user.dir");
        	resultpath = dir + "/"+RESULT_FILE_NAME+RESULT_FILE_ENDING;
        PrintWriter writer = new PrintWriter(new File(resultpath), "UTF-8");
        for(String step: steps) {
            writer.println(step);
        }
        System.out.println("Writing: Result file completed."); 
        System.out.println("------------------------------------------");
        writer.close();
    } catch(Exception e) {System.out.println("ERROR: Writing result file failed");System.out.println(e);};
}
	
public  ArrayList<String> addOutputTimestepData(ArrayList<String> steps, RealTimeContainer realTimeContainer, 
			IntegratorData integratorData, SensorSet sensorSet, int subIndx) {
	
			RealTimeResultSet realTimeResultSet = realTimeContainer.getRealTimeList().get(subIndx);
			MasterSet masterSet = realTimeContainer.getRealTimeList().get(subIndx).getMasterSet(); 
			SpaceShip spaceShip = masterSet.getSpaceShip();
			AtmosphereSet atmosphereSet = masterSet.getAtmosphereSet();
			AerodynamicSet aerodynamicSet = masterSet.getAerodynamicSet();
			GravitySet gravitySet = masterSet.getGravitySet();
			ControlCommandSet controlCommandSet = spaceShip.getgNCModel().getControlCommandSet();
			ForceMomentumSet forceMomentumSet = masterSet.getForceMomentumSet();
			ActuatorSet actuatorSet = masterSet.getActuatorSet();
			if(isPlot) {
			dataSet.addPair(new Pair((integratorData.getGlobalTime()+realTimeContainer.getRealTimeList().get(subIndx).getTime()), 
			sensorSet.getRealTimeResultSet().getEulerAngle().pitch*180/PI ));
			//	dataSet.addPair(new Pair(realTimeResultSet.getAltitude(), 
			//			aerodynamicSet.getFlowzone()));
			dataContainer.setxAxisLabel("Time");
			dataContainer.setyAxisLabel("Euler 1 (Sensor)");
			}
			
			Quaternion qVector = realTimeResultSet.getQuaternion();

	steps.add(realTimeResultSet.getGlobalTime() + " " + 
	realTimeResultSet.getLongitude() + " " + 
	realTimeResultSet.getLatitude() + " " + 
	realTimeResultSet.getAltitude() + " " + 
	realTimeResultSet.getAltitude()+ " " + 
	realTimeResultSet.getRadius() + " " + 
	realTimeResultSet.getVelocity()+ " " + 
	realTimeResultSet.getFpa() + " " + 
	realTimeResultSet.getAzi() + " " +     			
	atmosphereSet.getDensity() + " " + 
	atmosphereSet.getStaticTemperature()+ " " +
	atmosphereSet.getMach()+ " " +
	atmosphereSet.getGamma()+ " " +
	atmosphereSet.getGasConstant()+ " " +
	atmosphereSet.getStaticPressure()+ " " +
	atmosphereSet.getDynamicPressure()+ " " +
	aerodynamicSet.getFlowzone()+ " " +
	aerodynamicSet.getDragCoefficient()+ " " +
	aerodynamicSet.getLiftCoefficient()+ " " +
	aerodynamicSet.getSideForceCoefficient()+" "+
	aerodynamicSet.getDragForce() + " " +
	aerodynamicSet.getLiftForce() + " " +
	aerodynamicSet.getSideForce() + " " +
	aerodynamicSet.getAerodynamicAngleOfAttack()+" "+
	aerodynamicSet.getAerodynamicBankAngle()+ " " +     		
	gravitySet.getG_NED()[0][0]+" "+
	gravitySet.getG_NED()[1][0]+" "+
	gravitySet.getG_NED()[2][0]+" "+
	Math.sqrt(gravitySet.getG_NED()[0][0]*gravitySet.getG_NED()[0][0] + gravitySet.getG_NED()[1][0]*gravitySet.getG_NED()[1][0] + gravitySet.getG_NED()[2][0]*gravitySet.getG_NED()[2][0])+" "+     		  
	forceMomentumSet.getF_total_NED()[0][0]+" "+
	forceMomentumSet.getF_total_NED()[1][0]+" "+
	forceMomentumSet.getF_total_NED()[2][0]+" "+
	forceMomentumSet.getF_Aero_A()[0][0]+" "+
	forceMomentumSet.getF_Aero_A()[1][0]+" "+
	forceMomentumSet.getF_Aero_A()[2][0]+" "+
	forceMomentumSet.getF_Thrust_NED()[0][0]+" "+
	forceMomentumSet.getF_Thrust_NED()[1][0]+" "+
	forceMomentumSet.getF_Thrust_NED()[2][0]+" "+
	forceMomentumSet.getF_Gravity_NED()[0][0]+" "+
	forceMomentumSet.getF_Gravity_NED()[1][0]+" "+
	forceMomentumSet.getF_Gravity_NED()[2][0]+" "+
	realTimeResultSet.getCartesianPosECEF()[0]+" "+
	realTimeResultSet.getCartesianPosECEF()[1]+" "+
	realTimeResultSet.getCartesianPosECEF()[2]+" "+
	0 + " " + 
	0 + " " + 
	0 + " " +       	 	  
	qVector.w+" "+
	qVector.x+" "+
	qVector.y+" "+
	qVector.z+" "+
	realTimeResultSet.getPQR()[0][0]+" "+
	realTimeResultSet.getPQR()[1][0]+" "+
	realTimeResultSet.getPQR()[2][0]+" "+
	forceMomentumSet.getM_total_NED()[0][0]+" "+
	forceMomentumSet.getM_total_NED()[1][0]+" "+
	forceMomentumSet.getM_total_NED()[2][0]+" "+
	realTimeResultSet.getEulerAngle().roll+" "+
	realTimeResultSet.getEulerAngle().pitch+" "+
	realTimeResultSet.getEulerAngle().yaw+" "+
	realTimeResultSet.getMasterSet().getSpaceShip().getProperties().getMassAndInertia().getMass()+ " " +
	realTimeResultSet.getNormalizedDeceleration()+ " " +
	0+ " " + 
	realTimeResultSet.getVelocity()*Math.cos(realTimeResultSet.getFpa())+" "+
	realTimeResultSet.getVelocity()*Math.sin(realTimeResultSet.getFpa())+" "+
	//realTimeContainer.getRealTimeList().get(subIndx).getIntegratorData().getGroundtrack()/1000+" "+ 
	realTimeResultSet.getGroundtrack()/1000+" "+ 
	spaceShip.getgNCModel().getActiveSequence()+" "+
	sensorSet.getControllerTime()+" "+
	aerodynamicSet.getDragCoefficientParachute()+" "+
	aerodynamicSet.getDragForceParachute()+" "+
	(controlCommandSet.getPrimaryThrustThrottleCmd()*100)+ " "+ 
	(actuatorSet.getPrimaryThrust_is())+" "+
	(actuatorSet.getPrimaryThrust_is()/realTimeResultSet.getSCMass())+" "+
	realTimeResultSet.getMasterSet().getSpaceShip().getProperties().getPropulsion().getPrimaryPropellantFillingLevel()/realTimeResultSet.getMasterSet().getSpaceShip().getProperties().getPropulsion().getPrimaryPropellant()*100+" "+ 
	actuatorSet.getPrimaryISP_is()+" "+
	controlCommandSet.getMomentumRCS_X_cmd()+" "+
	controlCommandSet.getMomentumRCS_Y_cmd()+" "+
	controlCommandSet.getMomentumRCS_Z_cmd()+" "+
	actuatorSet.getMomentumRCS_X_is()+" "+
	actuatorSet.getMomentumRCS_Y_is()+" "+
	actuatorSet.getMomentumRCS_Z_is()+" "+
	realTimeResultSet.getMasterSet().getSpaceShip().getProperties().getPropulsion().getSecondaryPropellantFillingLevel()/realTimeResultSet.getMasterSet().getSpaceShip().getProperties().getPropulsion().getSecondaryPropellant()*100+" "+
	controlCommandSet.getTVC_alpha()+" "+
	controlCommandSet.getTVC_beta()+" "+
	actuatorSet.getTVC_alpha()+" "+
	actuatorSet.getTVC_beta()+" "+
	forceMomentumSet.getF_Thrust_B()[0][0]+" "+
	forceMomentumSet.getF_Thrust_B()[1][0]+" "+
	forceMomentumSet.getF_Thrust_B()[2][0]+" "+
	0+" "+
	0+" "+
	0+" "+
	spaceShip.getProperties().getPropulsion().getMassFlowPrimary()+" "+
	(spaceShip.getProperties().getPropulsion().getPrimaryPropellant()-spaceShip.getProperties().getPropulsion().getPrimaryPropellantFillingLevel())+" "+
	(spaceShip.getProperties().getPropulsion().getSecondaryPropellant()-spaceShip.getProperties().getPropulsion().getSecondaryPropellantFillingLevel())+" "+
	0+" "+
	0+" "+
	0+" "+
	0+" "+
	spaceShip.getProperties().getPropulsion().getAccumulatedDeltaVPrimary()+" "
	);
	return steps;	
}


}
