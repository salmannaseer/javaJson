package com.ibm.restapi;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
@Controller
@EnableAutoConfiguration
@RestController
@RequestMapping("/json")
public class DataPipeline{
	
	private ResultSet query_set= null;
	
	//List to store the data from SQL
	private List<CorrInfo> list = null;
	
	
	//Getting Data from the online SQL Database - Get Method
	@RequestMapping(value="/corpusData")
    @ResponseBody
    public List<CorrInfo> GetRequestProcessing(@RequestParam("q") String q) 
	{
		
		list = new ArrayList<CorrInfo>();
		try {
			query_set = new DatabaseCon().getData(q);
			
			try {
                while (query_set.next()) {
                    try {
                        String loc = query_set.getString("loc");
                        String zip =query_set.getString("zip");
                        String ts = query_set.getString("ts");
                        list.add(new CorrInfo(loc,zip,ts));
                    } catch (SQLException ex) {
                        Logger.getLogger(DataPipeline.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(DataPipeline.class.getName()).log(Level.SEVERE, null, ex);
            }
			
			return list;
		}
		catch(Exception e) {
			list.add(new CorrInfo("DB","Connection","Error"));
			return list;
		}
	}
	
	//Updating Data to the online SQL Database - POST Method
	@RequestMapping(method=RequestMethod.POST,value="/corpusUpdate")
    public UpdateStatus DoUpdate(@RequestBody CorrInfo ci){
		DatabaseCon c = new DatabaseCon();
		if(c.isNull(ci.getTS())){
			c.DoInsert(ci.getLoc(),ci.getZip(),ci.getTS());
			return new UpdateStatus("inserted");
		}else{
			c.DoUpdate(ci.getLoc(), ci.getZip(), ci.getTS());
			return new UpdateStatus("updated");
		}
    }
}