package main;

public class FilePaths {
    //-----------------------------------------------------------------------------------------------------------------------------------------
    //												Relative File Paths
    //-----------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * 		Result file -> Main output file 
	 */
    public static String RES_File    		= System.getProperty("user.dir") + "/results.txt"  ;       	 	// Input: result file 
    /**
     * 		Input files
     */
    public static String environmentFile    = System.getProperty("user.dir") + "/EnvironmentConfig.json";
    public static String spacecraftFile     = System.getProperty("user.dir") + "/SpacecraftConfig.json";   

}
