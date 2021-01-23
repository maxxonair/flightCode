package utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import FlightElement.SpaceShip;
import FlightElement.GNCModel.SequenceContent;
import Simulator_main.DataSets.IntegratorData;
import Simulator_main.DataSets.SimulatorInputSet;
import main.FilePaths;

public class InputReader {
/**
 * 
 * 		File path setting for each input file: 
 * 		The following file path setting will be used for the Read AND the Write class
 * 
 */

public  Quaternion qVector = new Quaternion(1,0,0,0);
	

public InputReader() {
	
}
//---------------------------------------------------------------------------------------------------
public  SimulatorInputSet readInput() throws IOException{
	System.out.println("Input Manager: Reading Input File started ... ");
    SpaceShip spaceShip = new SpaceShip(); 
    IntegratorData integratorData = new IntegratorData(); 
    SimulatorInputSet simulatorInputSet = new SimulatorInputSet();
    
	  JSONParser parser = new JSONParser();	  
	  //JSONObject a = (JSONObject) parser.parse(new FileReader(FilePaths.environmentFile));
		Object obj;
		try {
			
		  // Read json files to JSONObjet 	
		  obj = parser.parse(new FileReader(FilePaths.environmentFile));
		  JSONObject environment = (JSONObject) obj;  
		  obj = parser.parse(new FileReader(FilePaths.spacecraftFile));
		  JSONObject spacecraft = (JSONObject) obj;  
		  
		  // Display Simulation and File names:
		  System.out.println("Simulation: "+environment.get("FileName").toString());
		  System.out.println("Spacecraft: "+spacecraft.get("FileName").toString());
		  
		  // Write JSONOBJECT data to respective simulation classes> 
		  spaceShip = createSpaceship(spacecraft);
		  integratorData = createIntegratorData(environment);
		  
		  simulatorInputSet.setIntegratorData(integratorData);
		  simulatorInputSet.setSpaceShip(spaceShip);
		  
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    return simulatorInputSet;
}

private SpaceShip createSpaceship(JSONObject spacecraft) {
	SpaceShip spaceShip = new SpaceShip();
	// Container to load data
	double value = 0;
	JSONObject initialState = null;
	JSONObject propulsionConfig = null;
	JSONObject geometricConfig = null;
	JSONObject aeroConfig = null;
	JSONObject gncConfig = null;
	JSONObject sensorConfig = null;
	try {
		 initialState = (JSONObject) spacecraft.get("InitialState");
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	try {
		 propulsionConfig = (JSONObject) spacecraft.get("PropulsionConfig");
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	try {
		aeroConfig = (JSONObject) spacecraft.get("AeroConfig");
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	try {
		geometricConfig = (JSONObject) spacecraft.get("GeometricConfig");
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	try {
		 gncConfig = (JSONObject) spacecraft.get("GNCconfig");
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	try {
		 sensorConfig = (JSONObject) spacecraft.get("SensorConfig");
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	
	try {
		value = Double.parseDouble( initialState.get("InitialMass").toString() );
		spaceShip.getProperties().getMassAndInertia().setMass(value);
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	
	try {
		value = Double.parseDouble( initialState.get("Time_0_ET").toString() );
		spaceShip.getState().setGlobalTime(value);
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	
	try {
		value = Double.parseDouble( initialState.get("Position_IN_Longitude_deg").toString() );
		spaceShip.getState().setInitLongitude(value);
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	
	try {
		value = Double.parseDouble( initialState.get("Position_IN_Latitude_deg").toString() );
		spaceShip.getState().setInitLatitude(value);
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	
	try {
		value = Double.parseDouble( initialState.get("Position_IN_Radius").toString() );
		spaceShip.getState().setInitRadius(value);;
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	
	try {
		value = Double.parseDouble( initialState.get("Velocity_magnitude").toString() );
		spaceShip.getState().setInitVelocity(value);;
	} catch (Exception exp) {
		exp.printStackTrace();
	}

	try {
		value = Double.parseDouble( initialState.get("FlightPathAngle_deg").toString() );
		spaceShip.getState().setInitFpa(value);;
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	
	try {
		value = Double.parseDouble( initialState.get("Azimuth_deg").toString() );
		spaceShip.getState().setInitAzimuth(value);;
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	Quaternion initialAttitude = new Quaternion();
	try {
		value = Double.parseDouble( initialState.get("Attitude_Quaternion_IN_from_SCB_w").toString() );
		initialAttitude.w = value;
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	try {
		value = Double.parseDouble( initialState.get("Attitude_Quaternion_IN_from_SCB_x").toString() );
		initialAttitude.x = value;
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	try {
		value = Double.parseDouble( initialState.get("Attitude_Quaternion_IN_from_SCB_y").toString() );
		initialAttitude.y = value;
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	try {
		value = Double.parseDouble( initialState.get("Attitude_Quaternion_IN_from_SCB_z").toString() );
		initialAttitude.z = value;
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	spaceShip.getState().setInitialQuaternion(initialAttitude);
	
	try {
		value = Double.parseDouble( initialState.get("Attitude_Rate_SCB_wrt_IN_SCB_deg_s_x").toString() );
		spaceShip.getState().setInitRotationalRateX(value);
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	
	try {
		value = Double.parseDouble( initialState.get("Attitude_Rate_SCB_wrt_IN_SCB_deg_s_y").toString() );
		spaceShip.getState().setInitRotationalRateY(value);
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	
	try {
		value = Double.parseDouble( initialState.get("Attitude_Rate_SCB_wrt_IN_SCB_deg_s_z").toString() );
		spaceShip.getState().setInitRotationalRateZ(value);
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	
	// Read Aero Config
	try {
		value = Double.parseDouble( aeroConfig.get("SurfaceArea").toString() );
		spaceShip.getProperties().getAeroElements().setSurfaceArea(value);
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	// Read Propulsion Systems	
	try {

        JSONArray slideContent = (JSONArray) propulsionConfig.get("PropulsionSystems");
        Iterator i = slideContent.iterator();

        while (i.hasNext()) {
        	JSONObject propulsionSystem = (JSONObject) i.next();
        	
        	try {
        		value = Double.parseDouble( propulsionSystem.get("ISP_nominal_s").toString() );
        		spaceShip.getProperties().getPropulsion().setPrimaryISPMax(value);
        	} catch (Exception exp) {
        		exp.printStackTrace();
        	}
        }
	} catch (Exception exp ) {
		exp.printStackTrace();
	}
	// Read Sequence	
	List<SequenceContent> SequenceSet = new ArrayList<SequenceContent>();
	try {

        JSONArray sequenceC = (JSONArray) gncConfig.get("Sequence");
        Iterator sequence = sequenceC.iterator();
        int ID =0;
        while (sequence.hasNext()) {
        	JSONObject sequenceElement = (JSONObject) sequence.next();
    	    SequenceContent sequenceContent = new SequenceContent();
    	    sequenceContent.setID(ID);
        	String name="";
        	int fc =0;
        	int event =0;
        	int end = 0;
        	double endVal = 0;
        	try {
        		name =  sequenceElement.get("Name").toString() ;
        	} catch (Exception exp) {
        		exp.printStackTrace();
        	}
        	try {
        		fc =  Integer.parseInt( sequenceElement.get("FlightController_index").toString() );
        	} catch (Exception exp) {
        		exp.printStackTrace();
        	}
        	try {
        		event =  Integer.parseInt( sequenceElement.get("EventManagement_index").toString() );
        	} catch (Exception exp) {
        		exp.printStackTrace();
        	}
        	try {
        		end =  Integer.parseInt( sequenceElement.get("EndCondition_index").toString() );
        	} catch (Exception exp) {
        		exp.printStackTrace();
        	}
        	try {
        		endVal =  Double.parseDouble( sequenceElement.get("EndCondition_value").toString() );
        	} catch (Exception exp) {
        		exp.printStackTrace();
        	}
        	
	    	if(fc==0) {
		    		// No Selection!
		    } else if (fc==1) { // roll control
		    	sequenceContent.addRollControl();
		    } else if (fc==2) { // yaw control
		    	sequenceContent.addYawControl();
		    } else if (fc==3) { // pitch control
		    	sequenceContent.addPitchControl();
		    } else if (fc==4) { // roll stabilisation
		    	sequenceContent.addRollControl();
		    } else if (fc==5) { // full thrust
		    	sequenceContent.addPrimaryThrustControl();
		    } else if (fc==6) { // ascent controller
		    	sequenceContent.addAscentController();
		    } else if (fc==7) {
		    	// Empty spot
		    } else if (fc==8) { // external Controller
		    	String scriptName=""; // INSERT SCRIPT NAME method ->TBD!
		    	sequenceContent.addExternalControl(scriptName);
		    }
        	
    	    if(event==0) {
    	    	// No Selection!
    	    } else if (event==1) {
    	    	sequenceContent.addParachuteDeployment();
    	    } else if (event==2) {
    	    	sequenceContent.addParachuteSeparation();
    	    } else if (event==3) {
    	    	// Stage Separation tbd
    	    } else if (event==4) {
    	    	sequenceContent.addHeatShieldSeparation();
    	    }
  	    
    	    sequenceContent.setTriggerEnd(end, endVal);
    	    
    	    SequenceSet.add(sequenceContent);
        	ID++;
        }
	} catch (Exception exp ) {
		exp.printStackTrace();
	}
	   SequenceContent sequenceContent = new SequenceContent();
	   SequenceSet.add(sequenceContent);
	   spaceShip.getProperties().getSequence().setSequenceSet(SequenceSet);
	   
	return spaceShip;
}


private IntegratorData createIntegratorData(JSONObject environment) {
	IntegratorData integratorData = new IntegratorData(); 
	// Container to load data
	double value = 0;
	int intVal = 0;
	JSONObject integConfig = null;
	JSONObject envConfig = null;
	try {
		integConfig = (JSONObject) environment.get("IntegratorConfig");
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	try {
		envConfig = (JSONObject) environment.get("EnvironmentModel");
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	
	try {
		value = Double.parseDouble( integConfig.get("StepSize_s").toString() );
		integratorData.setFixedTimestep(value);
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	
	try {
		value = Double.parseDouble( integConfig.get("MinimumStepsize_s").toString() );
		integratorData.setMinTimestep(value);
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	
	try {
		value = Double.parseDouble( integConfig.get("MaximumStepsize_s").toString() );
		integratorData.setMaxTimestep(value);
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	
	try {
		value = Double.parseDouble( integConfig.get("AbsoluteTolerance").toString() );
		integratorData.setAbsTol(value);
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	
	try {
		value = Double.parseDouble( integConfig.get("RelativeTolerance").toString() );
		integratorData.setRelTol(value);
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	
	try {
		intVal = Integer.parseInt( integConfig.get("NumberOfSteps").toString() );
		integratorData.setNrSteps(intVal);
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	
	try {
		intVal = Integer.parseInt( integConfig.get("Integrator_index").toString() );
		integratorData.setIntegratorType(intVal);
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	
	try {
		value = Double.parseDouble( integConfig.get("IntegratorFrequency").toString() );
		integratorData.setEnvironmentFrequency(value);	
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	
	try {
		intVal = Integer.parseInt( integConfig.get("DegreeOfFreedom").toString() );
		integratorData.setDegreeOfFreedom(intVal);
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	
	try {
		intVal = Integer.parseInt( envConfig.get("TargetBody_indx").toString() );
		integratorData.setTargetBody(intVal);
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	
	try {
		value = Double.parseDouble( integConfig.get("IntegrationTime_s").toString() );
		integratorData.setMaxIntegTime(value);
		integratorData.setMaxGlobalTime(value);
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	
	try {
		intVal = Integer.parseInt( integConfig.get("CoordinateSystem_indx").toString() );
		integratorData.setVelocityVectorCoordSystem(intVal);
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	
	try {
		value = Double.parseDouble( envConfig.get("TargetBody_ReferenceElevation").toString() );
		integratorData.setRefElevation(value);
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	
	try {
		intVal = Integer.parseInt( envConfig.get("AtmosphericDragModel_indx").toString() );
		integratorData.setAeroDragModel(intVal);
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	
	try {
		intVal = Integer.parseInt( envConfig.get("ParachuteModel_indx").toString() );
		integratorData.setAeroParachuteModel(intVal);
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	
	try {
		value = Double.parseDouble( envConfig.get("ParachuteConstDragCoeff").toString() );
		integratorData.setConstParachuteCd(value);
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	
	return integratorData;
}

// Unit Tester
public static void main(String[] args) {
	//testWriteJson();
	InputReader inputReader = new InputReader();
	try {
		inputReader.readInput();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
}
}
}
