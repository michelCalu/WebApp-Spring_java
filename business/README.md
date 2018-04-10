# Business

## Parameters
Different claims can have different parameters, with different values for each municipality. Moreover, these parameters can change over time. In order to address this requirement dynamically, the ParameterService has been created. 

In short, this service reads parameters for each municipality from a configuration file (parameters.xml) ands stores them in a Map where the key is a relative XPath expression starting from the node "claimType". This way, all parameters are queryable in a inambiguous way. 

E.g. :

```xml
<parameters>
	<parametrizable id="Gembloux">
		<claimType id="cert-nationality">
			<activated>true</activated>
		</claimType>
```

will result in a parameter with key="activated" and value="true". 

Please note all parameterIds are queryable from the client-side, in this example by means of the URL "/parameters/Gembloux/cert-nationality?parameterId=activated". 
