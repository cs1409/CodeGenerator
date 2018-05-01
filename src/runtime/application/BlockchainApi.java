package runtime.application;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class BlockchainApi {

	public final static String MULTICHAINPASSWORD = "HxQ5dwH3jUwP1mS4ehZzjAL3nyx2jVrJ58u8Tv4q94T1";
	public final static String MULTICHAINUSER = "multichainrpc";
	public final static String MULTICHAINNODEPUBLICIP = "18.191.29.202";
	public final static int MULTICHAINPORTNUMBER = 7736;
	public final static String CHAIN = "chain1";
	

	public static JSONObject getItems(String key, String stream) {

		ArrayList<Object> params = new ArrayList<Object>();
		params.add(stream);
		params.add(key);

		JSONObject data = invokeRpc("GetItem", "liststreamkeyitems", params, CHAIN);
		return data;
	}

	public static JSONObject createNewStream(String streamName) {

		ArrayList<Object> params = new ArrayList<Object>();

		params.add("stream");
		params.add(streamName);
		params.add(true);	

		return invokeRpc("NewStream", "create", params, CHAIN);
	}

	public static JSONObject subscribeToStream(String streamName) {

		ArrayList<Object> params = new ArrayList<Object>();

		params.add(streamName);

		return invokeRpc("SubscribeToStream", "subscribe", params, CHAIN);
	}

	public static JSONObject addStreamItem(String key, JSONObject data, String chain, String stream, EventType event) {

		ArrayList<Object> params = new ArrayList<Object>();

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("json", data);

		params.add(stream);
		params.add(key);
		params.add(jsonObject);

		JSONObject returnedJson = invokeRpc("AddItem", "publish", params, CHAIN);

		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(String.format("%s has been succesfully written/updated on the blockchain according to the event %s", data.get("class"), data.get("event")));


		return returnedJson;
	}

	public static JSONObject getRawTransactionData(String txid) {

		ArrayList<Object> params = new ArrayList<Object>();

		params.add(txid);
		params.add(1);

		return invokeRpc("GetRawData", "getrawtransaction", params, CHAIN);
	}

	@SuppressWarnings("deprecation")
	private static JSONObject invokeRpc(String id, String method, ArrayList<Object> params, String chainName) {

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		HttpClient httpClient = httpClientBuilder.build();

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("method", method);
		jsonObject.put("chain_name", chainName);
		if(params != null && params.size() != 0) {
			jsonObject.put("params", params);
		}
		JSONObject responseJsonObj = null;
		try {
			CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

			credentialsProvider.setCredentials(
					new AuthScope(MULTICHAINNODEPUBLICIP, MULTICHAINPORTNUMBER),
					new UsernamePasswordCredentials(MULTICHAINUSER, MULTICHAINPASSWORD)
					);

			StringEntity myEntity = new StringEntity(jsonObject.toString());

			HttpPost httpPost = new HttpPost(String.format("http://%s:%s@%s:%s", MULTICHAINUSER, MULTICHAINPASSWORD, MULTICHAINNODEPUBLICIP, MULTICHAINPORTNUMBER)); 

			httpPost.setEntity(myEntity);

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();

			//System.out.println("------------"); //DEBUG LINES: UNCOMMENT FOR MULTICHAIN DEBUG INFORMATION
			//System.out.println(httpResponse.getStatusLine()); //DEBUG LINES: UNCOMMENT FOR MULTICHAIN DEBUG INFORMATION

			if(httpEntity != null) {
				// System.out.println("Response content length:" + httpEntity.getContentLength()); //DEBUG LINES: UNCOMMENT FOR MULTICHAIN DEBUG INFORMATION
			}

			JSONParser parser = new JSONParser();

			responseJsonObj = (JSONObject) parser.parse(EntityUtils.toString(httpEntity));

			//System.out.println(responseJsonObj.toString()); //DEBUG LINES: UNCOMMENT FOR MULTICHAIN DEBUG INFORMATION

		} catch(org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		} catch(ClientProtocolException e) {
			e.printStackTrace();
		} catch(ParseException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		finally {

			httpClient.getConnectionManager().shutdown();
		}	
		return responseJsonObj;
	}
}
