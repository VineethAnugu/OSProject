package com.vin.teja.Server1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.vin.teja.service.AddRequest;
import com.vin.teja.service.AliveRequest;
import com.vin.teja.service.AliveResponse;
import com.vin.teja.service.DeadRequest;
import com.vin.teja.service.DeadResponse;
import com.vin.teja.service.MinusRequest;
import com.vin.teja.service.Response;
import com.vin.teja.service.WhichRequest;
import com.vin.teja.service.WhichResponse;

@Endpoint
public class Endpoint1 {

    private Map<String, ServerInfo> services = new HashMap<>();
    
    private ServerInfo putInfo(String x, int y, String[] z) {
    	ServerInfo si = new ServerInfo();
    	si.setIpAddress(x);
    	si.setPort(y);
        si.setServiceNames(z);
        return si;
    }
    
    public Endpoint1() {
/*	    String[] service_list1 = new String[2];
	    service_list1[0]= "AddService";
	    service_list1[1]= "MinusService";
	    String[] service_list2 = new String[2];
	    service_list2[0]= "MulService";
	    service_list2[1]= "DivService";
	    String[] service_list3 = new String[2];
	    service_list3[0]= "AddService";
	    service_list3[1]= "MulService";
	    String[] service_list4 = new String[2];
	    service_list4[0]= "MinusService";
	    service_list4[1]= "DivService";
	    ServerInfo s1 = putInfo("localhost", 8082, service_list1);
	    services.put(s1.getKey(), s1);
	    services.put(2, putInfo("localhost", 8083, service_list2));
	    services.put(3, putInfo("localhost", 8084, service_list3));
	    services.put(4, putInfo("localhost", 8085, service_list4));*/
    }

    	
	@PayloadRoot(namespace = "http://teja.vin.com/service",    		
			localPart = "AddRequest")
	@ResponsePayload
	public Response AddRequest(@RequestPayload AddRequest addrequest) {
		Response response = new Response();

		float a = addrequest.getNum1();
		float b = addrequest.getNum2();
		response.setResNum(a+b);
		response.setComment("Success");
		return response;
	}

	@PayloadRoot(namespace = "http://teja.vin.com/service",    		
			localPart = "MinusRequest")
	@ResponsePayload
	public Response MinusRequest(@RequestPayload MinusRequest minusrequest) {
		Response response = new Response();

		float a = minusrequest.getNum1();
		float b = minusrequest.getNum2();
		response.setResNum(a-b);
		response.setComment("Success");
		return response;
	}
	
	@PayloadRoot(namespace = "http://teja.vin.com/service",    		
			localPart = "WhichRequest")
	@ResponsePayload
	public WhichResponse WhichRequest(@RequestPayload WhichRequest whichrequest) {
		WhichResponse whichresponse = new WhichResponse();
		String r = whichrequest.getServiceName();
		
		int temp = -1;
		
		for(int i = 1; i <= services.size(); i++) {
			if(Arrays.asList(services.get(i).getServiceNames()).contains(r)) {
				temp = i;
				break;
			}
		}
		
		ServerInfo s = services.get(temp);
		whichresponse.setIPAddress(s.getIpAddress());
		whichresponse.setPort(s.getPort());		
		return whichresponse;
	}
	
	@PayloadRoot(namespace = "http://teja.vin.com/service",    		
			localPart = "AliveRequest")
	@ResponsePayload
	public AliveResponse AliveRequest(@RequestPayload AliveRequest aliverequest) {
		AliveResponse aliveresponse = new AliveResponse();
		String a = aliverequest.getIPAddress();
		int b = aliverequest.getPort();
		String[] c = new String[10];
		
		ServerInfo s = new ServerInfo();
		s.setIpAddress(a);
		s.setPort(b);
		//TODO set service names
		
		this.services.put(s.getKey(), s);
		return aliveresponse;
	}
	
	@PayloadRoot(namespace = "http://teja.vin.com/service",    		
			localPart = "DeadRequest")
	@ResponsePayload
	public DeadResponse DeadRequest(@RequestPayload DeadRequest deadrequest) {
		DeadResponse deadresponse = new DeadResponse();
		String a = deadrequest.getIPAddress();
		int b = deadrequest.getPort();
		String key = a+":"+b;
		
		if( this.services.containsKey(key) )
			this.services.remove(key);
		else
			deadresponse.setComment("Server information of the retrieved IP address and port is not present in the registry.");
		
		return deadresponse;
	}

}
