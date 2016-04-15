package com.faceoffaerie.contants;

import com.faceoffaerie.activities.SaveFaeryCurrentDate;

import java.io.Serializable;

public class PlistInfo implements Serializable{
	private static 			final long serialVersionUID = 1L;    
	public int 				PID = 0;
	public String 			fileName = "";
	public String 			image = "";
	public String 			name = "";
	public String			date= ""+ SaveFaeryCurrentDate.faerySavedDate();
	public String 			reading = "";
	public int 				iPad_Left_Eye_x = 0;
	public int 				iPad_Mouth_y = 0;
	public int 				iPad_Right_Eye_x = 0;
	public int 				iPhone_Left_Eye_x = 0;
	public int 				iPhone_Mouth_y = 0;
	public int 				iPhone_Right_Eye_x = 0;
}
