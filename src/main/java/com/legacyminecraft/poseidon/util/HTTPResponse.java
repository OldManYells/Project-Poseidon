package com.legacyminecraft.poseidon.util;
import com.legacyminecraft.poseidon.api.*;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;

public class HTTPResponse
{
    private String response;
    private int responseCode;
    
    public HTTPResponse(String response, int responseCode)
    {
        this.response = response;
        this.responseCode = responseCode;
    }
    
    public String getResponse()
    {
        return response;
    }
    
    public int getResponseCode()
    {
        return responseCode;
    }
}
