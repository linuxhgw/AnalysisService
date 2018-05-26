package org.springframework.boot.AnalysisService;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Hello world!
 *
 */

@EnableDiscoveryClient
@SpringBootApplication
public class AppAnalysis 
{
    public static void main( String[] args )
    {  
    	new SpringApplicationBuilder(AppAnalysis.class).web(true).run(args);
    }
}
