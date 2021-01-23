package utils;

import java.io.BufferedReader;
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
import FlightElement.GNCModel.SequenceElement;
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

	 double[] IntegInput =  new double[5];
	
	public  Quaternion qVector = new Quaternion(1,0,0,0);
	
	 double[][] InertiaTensor   = {{   0 ,  0  ,   0},
			  					  		 {   0 ,  0  ,   0},
			  					  		 {   0 ,  0  ,   0}};  // Inertia Tensor []

public InputReader() {
	
}
	
public  String[] IntegratorInputPath = {System.getProperty("user.dir") + "/INP/INTEG/00_DormandPrince853Integrator.inp",
   			System.getProperty("user.dir") + "/INP/INTEG/01_ClassicalRungeKuttaIntegrator.inp",
   			System.getProperty("user.dir") + "/INP/INTEG/02_GraggBulirschStoerIntegrator.inp",
   			System.getProperty("user.dir") + "/INP/INTEG/03_AdamsBashfordIntegrator.inp"
};
   	
//------------------------------------------------------	-----------------------------------------------------
public  void updateSequenceElements(SequenceElement NewElement, List<SequenceElement> SEQUENCE_DATA){	   
		   if (SEQUENCE_DATA.size()==0){
				  SEQUENCE_DATA.add(NewElement); 
		   } else {
			boolean element_exist = false   ;
			  for(int i=0; i<SEQUENCE_DATA.size(); i++){
				  int ID_LIST    = SEQUENCE_DATA.get(i).get_sequence_ID();
				  int ID_ELEMENT = NewElement.get_sequence_ID();
						  if (ID_LIST == ID_ELEMENT){
							  // item exists -> Update
							  SEQUENCE_DATA.get(i).Update(NewElement.get_sequence_ID(),NewElement.get_trigger_end_type(), NewElement.get_trigger_end_value(),NewElement.get_sequence_type(),NewElement.get_sequence_controller_ID(), NewElement.get_ctrl_target_vel(), NewElement.get_ctrl_target_alt(), NewElement.get_ctrl_target_curve(),NewElement.get_sequence_TVCController_ID(),NewElement.get_TVC_ctrl_target_time(),NewElement.get_TVC_ctrl_target_fpa(),NewElement.get_TVC_ctrl_target_curve());
							  element_exist = true;
						  } 
			  }
			if (element_exist == false ){
				  // New item -> add to list  
				SEQUENCE_DATA.add(NewElement);
			}	  
		   } 
	   }	
//---------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------
public  List<SequenceContent> readSequenceFile() throws IOException{	
	System.out.println("Sequence Manager: Reading Sequences started ... ");
	List<SequenceContent> SequenceSet = new ArrayList<SequenceContent>();
	BufferedReader br = new BufferedReader(new FileReader(""));
   String strLine;
   String fcSeparator="\\|FlightControllerElements\\|";
   String eventSeparator="\\|EventManagementElements";
   String endSeparator="\\|EndElement\\|";
   try {
   while ((strLine = br.readLine()) != null )   {
       
       	String[] initSplit = strLine.split(fcSeparator);

       	String[] head = initSplit[0].split(" ");
       //System.out.pri
       	int  ID = Integer.parseInt(head[0]);
       	//String sequenceName = head[1];
       	int flightControllerIndex = Integer.parseInt(initSplit[1].split(" ")[1]);
       	String[] arr     = strLine.split(eventSeparator);
       	//System.out.println(arr[1]);
       	int eventIndex  = Integer.parseInt(arr[1].split(" ")[1]);
       	
       	String[] arr2   = strLine.split(endSeparator);
       	//System.out.println(arr2[1]);
       	int endIndex    = Integer.parseInt(arr2[1].split(" ")[1]);
       	double endValue = Double.parseDouble(arr2[1].split(" ")[2]);
       	
       	System.out.println("Sequence Manager Added Element: SequID "+ID+ ", FCID "+flightControllerIndex+", EVID "+eventIndex);
       	
       	//System.out.println(ID+" "+sequenceName+" "+flightControllerIndex+" "+eventIndex+" "+endIndex+" "+endValue);
    	    SequenceContent sequenceContent = new SequenceContent();
    	    sequenceContent.setID(ID);
	    	//---------------------------------------------------------------------------------------------------
	    	//					Flight Controller  
	    	//---------------------------------------------------------------------------------------------------
    	    if(flightControllerIndex==0) {
    	    		// No Selection!
    	    } else if (flightControllerIndex==1) { // roll control
    	    	sequenceContent.addRollControl();
    	    } else if (flightControllerIndex==2) { // yaw control
    	    	sequenceContent.addYawControl();
    	    } else if (flightControllerIndex==3) { // pitch control
    	    	sequenceContent.addPitchControl();
    	    } else if (flightControllerIndex==4) { // roll stabilisation
    	    	sequenceContent.addRollControl();
    	    } else if (flightControllerIndex==5) { // full thrust
    	    	sequenceContent.addPrimaryThrustControl();
    	    } else if (flightControllerIndex==6) { // ascent controller
    	    	sequenceContent.addAscentController();
    	    } else if (flightControllerIndex==7) {
    	    	// Empty spot
    	    } else if (flightControllerIndex==8) { // external Controller
    	    	String scriptName=""; // INSERT SCRIPT NAME method ->TBD!
    	    	sequenceContent.addExternalControl(scriptName);
    	    }
      	//---------------------------------------------------------------------------------------------------
      	//					Events
      	//---------------------------------------------------------------------------------------------------
    	    if(eventIndex==0) {
    	    	// No Selection!
    	    } else if (eventIndex==1) {
    	    	sequenceContent.addParachuteDeployment();
    	    } else if (eventIndex==2) {
    	    	sequenceContent.addParachuteSeparation();
    	    } else if (eventIndex==3) {
    	    	// Stage Separation tbd
    	    } else if (eventIndex==4) {
    	    	sequenceContent.addHeatShieldSeparation();
    	    }
        //---------------------------------------------------------------------------------------------------
        	//					Sequence End
        	//---------------------------------------------------------------------------------------------------   	    
    	    sequenceContent.setTriggerEnd(endIndex, endValue);
        //---------------------------------------------------------------------------------------------------
        	//					Add Sequence 
        	//---------------------------------------------------------------------------------------------------  
    	    String testString = ""+sequenceContent.getID();
    	    if(!testString.equals("")) {
    	    		SequenceSet.add(sequenceContent);
    	    }
   }
   br.close();
   } catch(NullPointerException eNPE) { System.out.println(eNPE);}
   // Add additional sequence element to avoid reaching undefined space 
   SequenceContent sequenceContent = new SequenceContent();
   SequenceSet.add(sequenceContent);
   System.out.println("Sequence Manager: Reading Sequences completed successfully.");
 return SequenceSet; 
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
		  
		  // Test outputs
		  System.out.println(spaceShip.getProperties().getMassAndInertia().getMass());
		  System.out.println(spaceShip.getState().getInitLongitude());
		  System.out.println(integratorData.getTargetBody());
		  
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
	
	// Read Propulsion Systems
	System.out.println(propulsionConfig.get("PropulsionSystems").toString());
	
	try {

        JSONArray slideContent = (JSONArray) propulsionConfig.get("PropulsionSystems");
        Iterator i = slideContent.iterator();

        while (i.hasNext()) {
        	JSONObject propulsionSystem = (JSONObject) i.next();
        	System.out.println(propulsionSystem.get("Name"));
        }
	} catch (Exception exp ) {
		exp.printStackTrace();
	}
	
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
		intVal = Integer.parseInt( envConfig.get("TargetBody_indx").toString() );
		integratorData.setTargetBody(intVal);
	} catch (Exception exp) {
		exp.printStackTrace();
	}
	
	return integratorData;
}
/*
private  SpaceShip checkSpaceShip(String identifier, double value, SpaceShip spaceShip) {
	if(identifier.equals("Init_LONG")) {
		spaceShip.getState().setInitLongitude(value*UConst.deg2rad);
	} else if (identifier.equals("Init_LAT")) {
		spaceShip.getState().setInitLatitude(value*UConst.deg2rad);
	} else if (identifier.equals("Init_RAD")) {
		spaceShip.getState().setInitRadius(value);
	} else if (identifier.equals("Init_VEL")) {
		spaceShip.getState().setInitVelocity(value);
	} else if (identifier.equals("Init_FPA")) {
		spaceShip.getState().setInitFpa(value*UConst.deg2rad);
	} else if (identifier.equals("Init_AZI")) {
		spaceShip.getState().setInitAzimuth(value*UConst.deg2rad);
	} else if (identifier.equals("Init_AngRateX")) {
		spaceShip.getState().setInitRotationalRateX(value*UConst.deg2rad);	
	} else if (identifier.equals("Init_AngRateY")) {
		spaceShip.getState().setInitRotationalRateY(value*UConst.deg2rad);	
	} else if (identifier.equals("Init_AngRateZ")) {
		spaceShip.getState().setInitRotationalRateZ(value*UConst.deg2rad);	
	} else if(identifier.equals("Init_Mass")) {
		spaceShip.getProperties().getMassAndInertia().setMass(value);
	} else if (identifier.equals("SC_COM")) {
		spaceShip.getProperties().getMassAndInertia().setCoM(value);
	} else if (identifier.equals("SC_COT")) {
		spaceShip.getProperties().getGeometry().setCoT(value);
	} else if (identifier.equals("SC_COP")) {
		spaceShip.setCoP(value);
	} else if (identifier.equals("SC_Height")) {
		spaceShip.getProperties().getGeometry().setVehicleLength(value);
	} else if (identifier.equals("SC_ParDiam")) {
		spaceShip.getProperties().getAeroElements().setParachuteSurfaceArea(UConst.PI*(value*value/4));
	} else if (identifier.equals("SC_BodyRadius")) {
		spaceShip.getProperties().getAeroElements().setHeatshieldRadius(value/2);
	} else if (identifier.equals("SC_ParMass")) {
    		spaceShip.getProperties().getAeroElements().setParachuteMass(value);
	} else if (identifier.equals("SC_HeatShieldMass")) {
		spaceShip.getProperties().getAeroElements().setHeatShieldMass(value);
	} else if (identifier.equals("SC_SurfArea")) {
		spaceShip.getProperties().getAeroElements().setSurfaceArea(UConst.PI*(value*value/4));
	} else if (identifier.equals("Init_IXX")) {
		InertiaTensor[0][0] = value;
	} else if (identifier.equals("Init_IXY")) {
		InertiaTensor[0][1] = value;
	} else if (identifier.equals("Init_IXZ")) {
		InertiaTensor[0][2] = value;
	} else if (identifier.equals("Init_IYX")) {
		InertiaTensor[1][0] = value;
	} else if (identifier.equals("Init_IYY")) {
		InertiaTensor[1][1] = value;
	} else if (identifier.equals("Init_IYZ")) {
		InertiaTensor[1][2] = value;
	} else if (identifier.equals("Init_IZX")) {
		InertiaTensor[2][0] = value;
	} else if (identifier.equals("Init_IZY")) {
		InertiaTensor[2][1] = value;
	} else if (identifier.equals("Init_IZZ")) {
		InertiaTensor[2][2] = value;
	} else if (identifier.equals("SC_MainISP")) {
		spaceShip.getProperties().getPropulsion().setPrimaryISPMax(value);
	} else if (identifier.equals("SC_MainProp")) {
		spaceShip.getProperties().getPropulsion().setPrimaryPropellant(value);
	} else if (identifier.equals("SC_MainThrustMax")) {
		spaceShip.getProperties().getPropulsion().setPrimaryThrustMax(value);
	} else if (identifier.equals("SC_MainThrustMin")) {
		spaceShip.getProperties().getPropulsion().setPrimaryThrustMin(value);
	} else if (identifier.equals("SC_RCSMomX")) {
		spaceShip.getProperties().getPropulsion().setRCSMomentumX(value);
	} else if (identifier.equals("SC_RCSMomY")) {
		spaceShip.getProperties().getPropulsion().setRCSMomentumY(value);
	} else if (identifier.equals("SC_RCSMomZ")) {
		spaceShip.getProperties().getPropulsion().setRCSMomentumZ(value);
	} else if (identifier.equals("SC_RCSProp")) {
		spaceShip.getProperties().getPropulsion().setSecondaryPropellant(value);
	} else if (identifier.equals("SC_RCSISPX")) {
		spaceShip.getProperties().getPropulsion().setSecondaryISP_RCS_X(value);
	} else if (identifier.equals("SC_RCSISPY")) {
		spaceShip.getProperties().getPropulsion().setSecondaryISP_RCS_Y(value);
	} else if (identifier.equals("SC_RCSISPZ")) {
		spaceShip.getProperties().getPropulsion().setSecondaryISP_RCS_Z(value);
	} else if (identifier.equals("SC_RCSThrustX")) {
		spaceShip.getProperties().getPropulsion().setSecondaryThrust_RCS_X(value);
	} else if (identifier.equals("SC_RCSThrustY")) {
		spaceShip.getProperties().getPropulsion().setSecondaryThrust_RCS_Y(value);
	} else if (identifier.equals("SC_RCSThrustZ")) {
		spaceShip.getProperties().getPropulsion().setSecondaryThrust_RCS_Z(value);
	} else if (identifier.equals("SC_MainISPModel")) {
    		if((int) value ==1) {
    			spaceShip.getProperties().getPropulsion().setIsPrimaryThrottleModel(true);
    		} else {
    			spaceShip.getProperties().getPropulsion().setIsPrimaryThrottleModel(false);	
    		}
	} else if (identifier.equals("SC_MainISPMin")) {
		spaceShip.getProperties().getPropulsion().setPrimaryISPMin(value);
	} 
	
	return spaceShip;
}

private  IntegratorData checkIntegratorData(String identifier, double value, IntegratorData integratorData) {
			if (identifier.equals("Integ_MaxTime")) {
		integratorData.setMaxGlobalTime(value);
		integratorData.setGlobalTime(0);
	} else if (identifier.equals("Integ_Integrator")) {

	} else if (identifier.equals("Integ_VelVector")) {
		integratorData.setVelocityVectorCoordSystem((int) value);	
	} else if (identifier.equals("Env_CenterBody")) {
		integratorData.setTargetBody((int) value);	
	} else if (identifier.equals("Env_RefElev")) {
		integratorData.setRefElevation( value);	
	} else if (identifier.equals("Env_CenterBody")) {
		integratorData.setTargetBody((int) value);	
	} else if (identifier.equals("Env_DragModel")) {
		integratorData.setAeroDragModel((int) value);	
	} else if (identifier.equals("Env_ParModel")) {
		integratorData.setAeroParachuteModel((int) value);	
	} else if (identifier.equals("Env_ParCD")) {
		integratorData.setConstParachuteCd( value);	
	} else if (identifier.equals("Integ_DoF")) {
		integratorData.setDegreeOfFreedom((int) value);	
	} else if (identifier.equals("Init_QuartW")) {
		qVector.w = value;	
	} else if (identifier.equals("Init_QuartX")) {
		qVector.x = value;	
	} else if (identifier.equals("Init_QuartY")) {
		qVector.y = value;	
	} else if (identifier.equals("Init_QuartZ")) {
		qVector.z = value;	
	} else if (identifier.equals("Integ_853_MinStep")) {
		IntegInput[0] = value;	
	} else if (identifier.equals("Integ_853_MaxStep")) {
		IntegInput[1] = value;	
	} else if (identifier.equals("Integ_853_AbsTol")) {
		IntegInput[2] = value;	
	} else if (identifier.equals("Integ_853_RelTol")) {
		IntegInput[3] = value;	
	} else if (identifier.equals("Integ_RungKut_Step")) {
		IntegInput[0] = value;	
	} else if (identifier.equals("Integ_GraBul_MinStep")) {
		IntegInput[0] = value;	
	} else if (identifier.equals("Integ_GraBul_MaxStep")) {
		IntegInput[1] = value;	
	} else if (identifier.equals("Integ_GraBul_AbsTol")) {
		IntegInput[2] = value;	
	} else if (identifier.equals("Integ_GraBul_RelTol")) {
		IntegInput[3] = value;	
	} else if (identifier.equals("Integ_AdBash_Steps")) {
		IntegInput[0] = value;	
	} else if (identifier.equals("Integ_AdBash_MinStep")) {
		IntegInput[1] = value;	
	} else if (identifier.equals("Integ_AdBash_MaxStep")) {
		IntegInput[2] = value;	
	} else if (identifier.equals("Integ_AdBash_AbsTol")) {
		IntegInput[3] = value;	
	} else if (identifier.equals("Integ_AdBash_RelTol")) {
		IntegInput[4] = value;	
	} else if (identifier.equals("Integ_Frequency")) {
		integratorData.setEnvironmentFrequency(value);	
		integratorData.setMaxIntegTime(1/value);
	} 
	
	return integratorData;
}
*/

}
