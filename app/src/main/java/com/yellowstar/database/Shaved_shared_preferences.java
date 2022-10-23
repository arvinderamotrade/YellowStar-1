package com.yellowstar.database;


import android.app.Activity;
import android.content.SharedPreferences;

public class Shaved_shared_preferences {
	Activity activity;
	public Shaved_shared_preferences(Activity activity)
	{
		this.activity=activity;
	}

	public void set_Login(int Login){
		SharedPreferences prefs=activity.getSharedPreferences("Login", activity.MODE_PRIVATE);
		SharedPreferences.Editor editor=prefs.edit();
		editor.putInt("Login", Login);
		editor.commit();
	}

	public int get_Login(){
		SharedPreferences prefs =activity.getSharedPreferences("Login",activity.MODE_PRIVATE);
		int login=prefs.getInt("Login", 0);
		return login;
	}


	public void set_Uname(String Uname){
		SharedPreferences prefs=activity.getSharedPreferences("Uname", activity.MODE_PRIVATE);
		SharedPreferences.Editor editor=prefs.edit();
		editor.putString("Uname", Uname);
		editor.commit();
	}

	public String get_Uname(){
		SharedPreferences prefs =activity.getSharedPreferences("Uname",activity.MODE_PRIVATE);
		String Uname=prefs.getString("Uname", "");
		return Uname;
	}

	public void set_State(String State){
		SharedPreferences prefs=activity.getSharedPreferences("State", activity.MODE_PRIVATE);
		SharedPreferences.Editor editor=prefs.edit();
		editor.putString("State", State);
		editor.commit();
	}

	public String get_State(){
		SharedPreferences prefs =activity.getSharedPreferences("State",activity.MODE_PRIVATE);
		String State=prefs.getString("State", "");
		return State;
	}


	public void set_District(String District){
		SharedPreferences prefs=activity.getSharedPreferences("District", activity.MODE_PRIVATE);
		SharedPreferences.Editor editor=prefs.edit();
		editor.putString("District", District);
		editor.commit();
	}

	public String get_District(){
		SharedPreferences prefs =activity.getSharedPreferences("District",activity.MODE_PRIVATE);
		String District=prefs.getString("District", "");
		return District;
	}

	public void set_DistrictID(String DistrictID){
		SharedPreferences prefs=activity.getSharedPreferences("DistrictID", activity.MODE_PRIVATE);
		SharedPreferences.Editor editor=prefs.edit();
		editor.putString("DistrictID", DistrictID);
		editor.commit();
	}

	public String get_DistrictID(){
		SharedPreferences prefs =activity.getSharedPreferences("DistrictID",activity.MODE_PRIVATE);
		String DistrictID=prefs.getString("DistrictID", "");
		return DistrictID;
	}

	public void set_Block(String Block){
		SharedPreferences prefs=activity.getSharedPreferences("Block", activity.MODE_PRIVATE);
		SharedPreferences.Editor editor=prefs.edit();
		editor.putString("Block", Block);
		editor.commit();
	}

	public String get_Block(){
		SharedPreferences prefs =activity.getSharedPreferences("Block",activity.MODE_PRIVATE);
		String Block=prefs.getString("Block", "");
		return Block;
	}


	public void set_BlockID(String BlockID){
		SharedPreferences prefs=activity.getSharedPreferences("BlockID", activity.MODE_PRIVATE);
		SharedPreferences.Editor editor=prefs.edit();
		editor.putString("BlockID", BlockID);
		editor.commit();
	}

	public String get_BlockID(){
		SharedPreferences prefs =activity.getSharedPreferences("BlockID",activity.MODE_PRIVATE);
		String BlockID=prefs.getString("BlockID", "");
		return BlockID;
	}

	public void set_Punch(String Punch){
		SharedPreferences prefs=activity.getSharedPreferences("Punch", activity.MODE_PRIVATE);
		SharedPreferences.Editor editor=prefs.edit();
		editor.putString("Punch", Punch);
		editor.commit();
	}

	public String get_Punch(){
		SharedPreferences prefs =activity.getSharedPreferences("Punch",activity.MODE_PRIVATE);
		String Punch=prefs.getString("Punch", "");
		return Punch;
	}

	public void set_PunchID(String PunchID){
		SharedPreferences prefs=activity.getSharedPreferences("PunchID", activity.MODE_PRIVATE);
		SharedPreferences.Editor editor=prefs.edit();
		editor.putString("PunchID", PunchID);
		editor.commit();
	}

	public String get_PunchID()
	{
		SharedPreferences prefs =activity.getSharedPreferences("PunchID",activity.MODE_PRIVATE);
		String PunchID=prefs.getString("PunchID", "");
		return PunchID;
	}


	public void set_Ward(String Punch){
		SharedPreferences prefs=activity.getSharedPreferences("Ward", activity.MODE_PRIVATE);
		SharedPreferences.Editor editor=prefs.edit();
		editor.putString("Ward", Punch);
		editor.commit();
	}

	public String get_Ward(){
		SharedPreferences prefs =activity.getSharedPreferences("Ward",activity.MODE_PRIVATE);
		String Ward=prefs.getString("Ward", "");
		return Ward;
	}



	public void set_WardID(String Punch){
		SharedPreferences prefs=activity.getSharedPreferences("WardID", activity.MODE_PRIVATE);
		SharedPreferences.Editor editor=prefs.edit();
		editor.putString("WardID", Punch);
		editor.commit();
	}

	public String get_WardID(){
		SharedPreferences prefs =activity.getSharedPreferences("WardID",activity.MODE_PRIVATE);
		String Ward=prefs.getString("WardID", "");
		return Ward;
	}


	public void set_Dev(String Dev){
		SharedPreferences prefs=activity.getSharedPreferences("Dev", activity.MODE_PRIVATE);
		SharedPreferences.Editor editor=prefs.edit();
		editor.putString("Dev", Dev);
		editor.commit();
	}

	public String get_Dev(){
		SharedPreferences prefs =activity.getSharedPreferences("Dev",activity.MODE_PRIVATE);
		String Dev=prefs.getString("Dev", "");
		return Dev;
	}

	public void set_SID(String SID){
		SharedPreferences prefs=activity.getSharedPreferences("SID", activity.MODE_PRIVATE);
		SharedPreferences.Editor editor=prefs.edit();
		editor.putString("SID", SID);
		editor.commit();
	}

	public String get_SID(){
		SharedPreferences prefs =activity.getSharedPreferences("SID",activity.MODE_PRIVATE);
		String SID=prefs.getString("SID", "");
		return SID;
	}

	public void set_Branch(String Branch){
		SharedPreferences prefs=activity.getSharedPreferences("Branch", activity.MODE_PRIVATE);
		SharedPreferences.Editor editor=prefs.edit();
		editor.putString("Branch", Branch);
		editor.commit();
	}

	public String get_Branch(){
		SharedPreferences prefs =activity.getSharedPreferences("Branch",activity.MODE_PRIVATE);
		String Branch=prefs.getString("Branch", "");
		return Branch;
	}

	public void set_PID(String PID){
		SharedPreferences prefs=activity.getSharedPreferences("PID", activity.MODE_PRIVATE);
		SharedPreferences.Editor editor=prefs.edit();
		editor.putString("PID", PID);
		editor.commit();
	}

	public String get_PID(){
		SharedPreferences prefs =activity.getSharedPreferences("PID",activity.MODE_PRIVATE);
		String PID=prefs.getString("PID", "");
		return PID;
	}

	public void set_Location(String PID){
		SharedPreferences prefs=activity.getSharedPreferences("Location", activity.MODE_PRIVATE);
		SharedPreferences.Editor editor=prefs.edit();
		editor.putString("Location", PID);
		editor.commit();
	}

	public String get_Location(){
		SharedPreferences prefs =activity.getSharedPreferences("Location",activity.MODE_PRIVATE);
		String Location=prefs.getString("Location", "");
		return Location;
	}

	public void set_Location_(String PID){
		SharedPreferences prefs=activity.getSharedPreferences("Location_", activity.MODE_PRIVATE);
		SharedPreferences.Editor editor=prefs.edit();
		editor.putString("Location_", PID);
		editor.commit();
	}

	public String get_Location_(){
		SharedPreferences prefs =activity.getSharedPreferences("Location_",activity.MODE_PRIVATE);
		String Location=prefs.getString("Location_", "");
		return Location;
	}

	public void set_LocationLatLng(String PID){
		SharedPreferences prefs=activity.getSharedPreferences("LocationLatLng", activity.MODE_PRIVATE);
		SharedPreferences.Editor editor=prefs.edit();
		editor.putString("LocationLatLng", PID);
		editor.commit();
	}

	public String get_LocationLatLng(){
		SharedPreferences prefs =activity.getSharedPreferences("LocationLatLng",activity.MODE_PRIVATE);
		String LocationLatLng=prefs.getString("LocationLatLng", "");
		return LocationLatLng;
	}

    public void CLEAR()
	{
		activity.getSharedPreferences("State",activity.MODE_PRIVATE).edit().putString("State","").commit();
		activity.getSharedPreferences("District",activity.MODE_PRIVATE).edit().putString("District","").commit();
		activity.getSharedPreferences("Block",activity.MODE_PRIVATE).edit().putString("Block","").commit();
    }
}



