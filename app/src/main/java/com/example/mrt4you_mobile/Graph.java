package com.example.mrt4you_mobile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.mrt4you_mobile.BaseActivity.NONOPERATIONALSTATIONS_URL;

public class Graph 
{
    private Set<Node> nodes = new HashSet<>();
    public void addNode(Node nodeA) 
    {
        nodes.add(nodeA);
    }
	public Set<Node> getNodes() 
	{
		return nodes;
	}
	public void setNodes(Set<Node> nodes) 
	{
		this.nodes = nodes;
	}


	public Node findNode(String nodeName)
	{
		Optional<Node> node = nodes.stream().filter(x -> x.getName().equalsIgnoreCase(nodeName)).findFirst();
		return node.isPresent() ? node.get() : null;
	}
	
	public static Graph createMRTGraph()
	{
		Node jurongEast = new Node("Jurong East");
		jurongEast.setStationCode(new ArrayList<>(Arrays.asList("NS1", "EW24")));
		Node bukitBatok = new Node("Bukit Batok");
		bukitBatok.setStationCode(new ArrayList<>(Arrays.asList("NS2")));
		Node bukitGombak = new Node("Bukit Gombak");
		bukitGombak.setStationCode(new ArrayList<>(Arrays.asList("NS3")));
		Node choaChuKang = new Node("Choa Chu Kang");
		choaChuKang.setStationCode(new ArrayList<>(Arrays.asList("NS4")));
		Node yewTee = new Node("Yew Tee");
		yewTee.setStationCode(new ArrayList<>(Arrays.asList("NS5")));
		Node kranji = new Node("Kranji");
		kranji.setStationCode(new ArrayList<>(Arrays.asList("NS7")));
		Node marsiling = new Node("Marsiling");
		marsiling.setStationCode(new ArrayList<>(Arrays.asList("NS8")));
		Node woodlands = new Node("Woodlands");
		woodlands.setStationCode(new ArrayList<>(Arrays.asList("NS9")));
		Node admiralty = new Node("Admiralty");
		admiralty.setStationCode(new ArrayList<>(Arrays.asList("NS10")));
		Node sembawang = new Node("Sembawang");
		sembawang.setStationCode(new ArrayList<>(Arrays.asList("NS11")));
		Node canberra = new Node("Canberra");
		canberra.setStationCode(new ArrayList<>(Arrays.asList("NS12")));
		Node yishun = new Node("Yishun");
		yishun.setStationCode(new ArrayList<>(Arrays.asList("NS13")));
		Node khatib = new Node("Khatib");
		khatib.setStationCode(new ArrayList<>(Arrays.asList("NS14")));
		Node yioChuKang = new Node("Yio Chu Kang");
		yioChuKang.setStationCode(new ArrayList<>(Arrays.asList("NS15")));
		Node angMoKio = new Node("Ang Mo Kio");
		angMoKio.setStationCode(new ArrayList<>(Arrays.asList("NS16")));
		Node bishan = new Node("Bishan");
		bishan.setStationCode(new ArrayList<>(Arrays.asList("NS17", "CC15")));
		Node braddell = new Node("Braddell");
		braddell.setStationCode(new ArrayList<>(Arrays.asList("NS18")));
		Node toaPayoh = new Node("Toa Payoh");
		toaPayoh.setStationCode(new ArrayList<>(Arrays.asList("NS19")));
		Node novena = new Node("Novena");
		novena.setStationCode(new ArrayList<>(Arrays.asList("NS20")));
		Node newton = new Node("Newton");
		newton.setStationCode(new ArrayList<>(Arrays.asList("NS21")));
		Node orchard = new Node("Orchard");
		orchard.setStationCode(new ArrayList<>(Arrays.asList("NS22")));
		Node somerset = new Node("Somerset");
		somerset.setStationCode(new ArrayList<>(Arrays.asList("NS23")));
		Node dhobyGhaut = new Node("Dhoby Ghaut");
		dhobyGhaut.setStationCode(new ArrayList<>(Arrays.asList("NS24", "CC1")));
		Node cityHall = new Node("City Hall");
		cityHall.setStationCode(new ArrayList<>(Arrays.asList("NS25", "EW13")));
		Node rafflesPlace = new Node("Raffles Place");
		rafflesPlace.setStationCode(new ArrayList<>(Arrays.asList("NS26", "EW14")));
		Node marinaBay = new Node("Marina Bay");
		marinaBay.setStationCode(new ArrayList<>(Arrays.asList("NS27")));
		Node marinaSouthPier = new Node("Marina South Pier");
		marinaSouthPier.setStationCode(new ArrayList<>(Arrays.asList("NS28")));
		
		jurongEast.addDestination(bukitBatok, 3);
		bukitBatok.addDestination(jurongEast, 3);
		
		bukitBatok.addDestination(bukitGombak, 2);
		bukitGombak.addDestination(bukitBatok, 2);
		
		bukitGombak.addDestination(choaChuKang, 4);
		choaChuKang.addDestination(bukitGombak, 4);
		
		choaChuKang.addDestination(yewTee, 3);
		yewTee.addDestination(choaChuKang, 3);
		
		yewTee.addDestination(kranji, 5);
		kranji.addDestination(yewTee, 5);
		
		kranji.addDestination(marsiling, 3);
		marsiling.addDestination(kranji, 3);
		
		marsiling.addDestination(woodlands, 2);
		woodlands.addDestination(marsiling, 2);
		
		woodlands.addDestination(admiralty, 3);
		admiralty.addDestination(woodlands, 3);
		
		admiralty.addDestination(sembawang, 3);
		sembawang.addDestination(admiralty, 3);
		
		sembawang.addDestination(canberra, 3);
		canberra.addDestination(sembawang, 3);
		
		canberra.addDestination(yishun, 3);
		yishun.addDestination(canberra, 3);
		
		yishun.addDestination(khatib, 2);
		khatib.addDestination(yishun, 2);
		
		khatib.addDestination(yioChuKang, 6);
		yioChuKang.addDestination(khatib, 6);
		
		yioChuKang.addDestination(angMoKio, 2);
		angMoKio.addDestination(yioChuKang, 2);
		
		angMoKio.addDestination(bishan, 4);
		bishan.addDestination(angMoKio, 4);
		
		bishan.addDestination(braddell, 2);
		braddell.addDestination(bishan, 2);
		
		braddell.addDestination(toaPayoh, 2);
		toaPayoh.addDestination(braddell, 2);
		
		toaPayoh.addDestination(novena, 3);
		novena.addDestination(toaPayoh, 3);
		
		novena.addDestination(newton, 2);
		newton.addDestination(novena, 2);
		
		newton.addDestination(orchard, 3);
		orchard.addDestination(newton, 3);
		
		orchard.addDestination(somerset, 2);
		somerset.addDestination(orchard, 2);
		
		somerset.addDestination(dhobyGhaut, 2);
		dhobyGhaut.addDestination(somerset, 2);
		
		dhobyGhaut.addDestination(cityHall, 3);
		cityHall.addDestination(dhobyGhaut, 3);
		
		cityHall.addDestination(rafflesPlace, 2);
		rafflesPlace.addDestination(cityHall, 2);
		
		rafflesPlace.addDestination(marinaBay, 2);
		marinaBay.addDestination(rafflesPlace, 2);
		
		marinaBay.addDestination(marinaSouthPier, 3);
		marinaSouthPier.addDestination(marinaBay, 3);
		
		Node pasirRis = new Node("Pasir Ris");
		pasirRis.setStationCode(new ArrayList<>(Arrays.asList("EW1")));
		Node tampines = new Node("Tampines");
		tampines.setStationCode(new ArrayList<>(Arrays.asList("EW2")));
		Node simei = new Node("Simei");
		simei.setStationCode(new ArrayList<>(Arrays.asList("EW3")));
		Node tanahMerah = new Node("Tanah Merah");
		tanahMerah.setStationCode(new ArrayList<>(Arrays.asList("EW4")));
		Node bedok = new Node("Bedok");
		bedok.setStationCode(new ArrayList<>(Arrays.asList("EW5")));
		Node kembangan = new Node("Kembangan");
		kembangan.setStationCode(new ArrayList<>(Arrays.asList("EW6")));
		Node eunos = new Node("Eunos");
		eunos.setStationCode(new ArrayList<>(Arrays.asList("EW7")));
		Node payaLebar = new Node("Paya Lebar");
		payaLebar.setStationCode(new ArrayList<>(Arrays.asList("EW8", "CC9")));
		Node aljunied = new Node("Aljunied");
		aljunied.setStationCode(new ArrayList<>(Arrays.asList("EW9")));
		Node kallang = new Node("Kallang");
		kallang.setStationCode(new ArrayList<>(Arrays.asList("EW10")));
		Node lavender = new Node("Lavender");
		lavender.setStationCode(new ArrayList<>(Arrays.asList("EW11")));
		Node bugis = new Node("Bugis");
		bugis.setStationCode(new ArrayList<>(Arrays.asList("EW12")));
		Node tanjongPagar = new Node("Tanjong Pagar");
		tanjongPagar.setStationCode(new ArrayList<>(Arrays.asList("EW15")));
		Node outramPark = new Node("Outram Park");
		outramPark.setStationCode(new ArrayList<>(Arrays.asList("EW16")));
		Node tiongBahru = new Node("Tiong Bahru");
		tiongBahru.setStationCode(new ArrayList<>(Arrays.asList("EW17")));
		Node redhill = new Node("Redhill");
		redhill.setStationCode(new ArrayList<>(Arrays.asList("EW18")));
		Node queenstown = new Node("Queenstown");
		queenstown.setStationCode(new ArrayList<>(Arrays.asList("EW19")));
		Node commonwealth = new Node("Commonwealth");
		commonwealth.setStationCode(new ArrayList<>(Arrays.asList("EW20")));
		Node buonaVista = new Node("Buona Vista");
		buonaVista.setStationCode(new ArrayList<>(Arrays.asList("EW21", "CC22")));
		Node dover = new Node("Dover");
		dover.setStationCode(new ArrayList<>(Arrays.asList("EW22")));
		Node clementi = new Node("Clementi");
		clementi.setStationCode(new ArrayList<>(Arrays.asList("EW23")));
		Node chineseGarden = new Node("Chinese Garden");
		chineseGarden.setStationCode(new ArrayList<>(Arrays.asList("EW25")));
		Node lakeSide = new Node("Lakeside");
		lakeSide.setStationCode(new ArrayList<>(Arrays.asList("EW26")));
		Node boonLay = new Node("Boon Lay");
		boonLay.setStationCode(new ArrayList<>(Arrays.asList("EW27")));
		Node pioneer = new Node("Pioneer");
		pioneer.setStationCode(new ArrayList<>(Arrays.asList("EW28")));
		Node jooKoon = new Node("Joo Koon");
		jooKoon.setStationCode(new ArrayList<>(Arrays.asList("EW29")));
		Node gulCircle = new Node("Gul Circle");
		gulCircle.setStationCode(new ArrayList<>(Arrays.asList("EW30")));
		Node tuasCrescent = new Node("Tuas Crescent");
		tuasCrescent.setStationCode(new ArrayList<>(Arrays.asList("EW31")));
		Node tuasWestRoad = new Node("Tuas West Road");
		tuasWestRoad.setStationCode(new ArrayList<>(Arrays.asList("EW32")));
		Node tuasLink = new Node("Tuas Link");
		tuasLink.setStationCode(new ArrayList<>(Arrays.asList("EW33")));
		
		pasirRis.addDestination(tampines, 3);
		tampines.addDestination(pasirRis, 3);
		
		tampines.addDestination(simei, 3);
		simei.addDestination(tampines, 3);
		
		simei.addDestination(tanahMerah, 3);
		tanahMerah.addDestination(simei, 3);
		
		tanahMerah.addDestination(bedok, 3);
		bedok.addDestination(tanahMerah, 3);
		
		bedok.addDestination(kembangan, 3);
		kembangan.addDestination(bedok, 3);
		
		kembangan.addDestination(eunos, 3);
		eunos.addDestination(kembangan, 3);
		
		eunos.addDestination(payaLebar, 2);
		payaLebar.addDestination(eunos, 2);
		
		payaLebar.addDestination(aljunied, 2);
		aljunied.addDestination(payaLebar, 2);
		
		aljunied.addDestination(kallang, 3);
		kallang.addDestination(aljunied, 3);
		
		kallang.addDestination(lavender, 2);
		lavender.addDestination(kallang, 2);
		
		lavender.addDestination(bugis, 2);
		bugis.addDestination(lavender, 2);
		
		bugis.addDestination(cityHall, 3);
		cityHall.addDestination(bugis, 3);
		
		cityHall.addDestination(rafflesPlace, 2);
		rafflesPlace.addDestination(cityHall, 2);
		
		rafflesPlace.addDestination(tanjongPagar, 3);
		tanjongPagar.addDestination(rafflesPlace, 3);
		
		tanjongPagar.addDestination(outramPark, 2);
		outramPark.addDestination(tanjongPagar, 2);
		
		outramPark.addDestination(tiongBahru, 3);
		tiongBahru.addDestination(outramPark, 3);
		
		tiongBahru.addDestination(redhill, 2);
		redhill.addDestination(tiongBahru, 2);
		
		redhill.addDestination(queenstown, 3);
		queenstown.addDestination(redhill, 3);
		
		queenstown.addDestination(commonwealth, 2);
		commonwealth.addDestination(queenstown, 2);
		
		commonwealth.addDestination(buonaVista, 2);
		buonaVista.addDestination(commonwealth, 2);
		
		buonaVista.addDestination(dover, 3);
		dover.addDestination(buonaVista, 3);
		
		dover.addDestination(clementi, 3);
		clementi.addDestination(dover, 3);
		
		clementi.addDestination(jurongEast, 4);
		jurongEast.addDestination(clementi, 4);
		
		jurongEast.addDestination(chineseGarden, 3);
		chineseGarden.addDestination(jurongEast, 3);
		
		chineseGarden.addDestination(lakeSide, 2);
		lakeSide.addDestination(chineseGarden, 2);
		
		lakeSide.addDestination(boonLay, 3);
		boonLay.addDestination(lakeSide, 3);
		
		boonLay.addDestination(pioneer, 2);
		pioneer.addDestination(boonLay, 2);
		
		pioneer.addDestination(jooKoon, 4);
		jooKoon.addDestination(pioneer, 4);
		
		jooKoon.addDestination(gulCircle, 3);
		gulCircle.addDestination(jooKoon, 3);
		
		gulCircle.addDestination(tuasCrescent, 3);
		tuasCrescent.addDestination(gulCircle, 3);
		
		tuasCrescent.addDestination(tuasWestRoad, 8);
		tuasWestRoad.addDestination(tuasCrescent, 8);
		
		tuasWestRoad.addDestination(tuasLink, 12);
		tuasLink.addDestination(tuasWestRoad, 12);
		
		Node brasBasah = new Node("Bras Basah");
		brasBasah.setStationCode(new ArrayList<>(Arrays.asList("CC2")));
		Node esplanade = new Node("Esplanade");
		esplanade.setStationCode(new ArrayList<>(Arrays.asList("CC3")));
		Node promenade = new Node("Promenade");
		promenade.setStationCode(new ArrayList<>(Arrays.asList("CC4")));
		Node nicollHighway = new Node("Nicoll Highway");
		nicollHighway.setStationCode(new ArrayList<>(Arrays.asList("CC5")));
		Node stadium = new Node("Stadium");
		stadium.setStationCode(new ArrayList<>(Arrays.asList("CC6")));
		Node mountbatten = new Node("Mountbatten");
		mountbatten.setStationCode(new ArrayList<>(Arrays.asList("CC7")));
		Node dakota = new Node("Dakota");
		dakota.setStationCode(new ArrayList<>(Arrays.asList("CC8")));
		Node macpherson = new Node("MacPherson");
		macpherson.setStationCode(new ArrayList<>(Arrays.asList("CC10")));
		Node taiSeng = new Node("Tai Seng");
		taiSeng.setStationCode(new ArrayList<>(Arrays.asList("CC11")));
		Node bartley = new Node("Bartley");
		bartley.setStationCode(new ArrayList<>(Arrays.asList("CC12")));
		Node serangoon = new Node("Serangoon");
		serangoon.setStationCode(new ArrayList<>(Arrays.asList("CC13")));
		Node lorongChuan = new Node("Lorong Chuan");
		lorongChuan.setStationCode(new ArrayList<>(Arrays.asList("CC14")));
		Node marymount = new Node("Marymount");
		marymount.setStationCode(new ArrayList<>(Arrays.asList("CC16")));
		Node caldecott = new Node("Caldecott");
		caldecott.setStationCode(new ArrayList<>(Arrays.asList("CC17")));
		Node botanicGardens = new Node("Botanic Gardens");
		botanicGardens.setStationCode(new ArrayList<>(Arrays.asList("CC19")));
		Node farrerRoad = new Node("Farrer Road");
		farrerRoad.setStationCode(new ArrayList<>(Arrays.asList("CC20")));
		Node hollandVillage = new Node("Holland Village");
		hollandVillage.setStationCode(new ArrayList<>(Arrays.asList("CC21")));
		Node onenorth = new Node("one-north");
		onenorth.setStationCode(new ArrayList<>(Arrays.asList("CC23")));
		Node kentRidge = new Node("Kent Ridge");
		kentRidge.setStationCode(new ArrayList<>(Arrays.asList("CC24")));
		Node hawParVilla = new Node("Haw Par Villa");
		hawParVilla.setStationCode(new ArrayList<>(Arrays.asList("CC25")));
		Node pasirPanjang = new Node("Pasir Panjang");
		pasirPanjang.setStationCode(new ArrayList<>(Arrays.asList("CC26")));
		Node labradorPark = new Node("Labrador Park");
		labradorPark.setStationCode(new ArrayList<>(Arrays.asList("CC27")));
		Node telokBlangah = new Node("Telok Blangah");
		telokBlangah.setStationCode(new ArrayList<>(Arrays.asList("CC28")));
		Node harbourfront = new Node("HarbourFront");
		harbourfront.setStationCode(new ArrayList<>(Arrays.asList("CC29")));
		
		dhobyGhaut.addDestination(brasBasah, 2);
		brasBasah.addDestination(dhobyGhaut, 2);
		
		brasBasah.addDestination(esplanade, 2);
		esplanade.addDestination(brasBasah, 2);
		
		esplanade.addDestination(promenade, 2);
		promenade.addDestination(esplanade, 2);
		
		promenade.addDestination(nicollHighway, 2);
		nicollHighway.addDestination(promenade, 2);
		
		nicollHighway.addDestination(stadium, 2);
		stadium.addDestination(nicollHighway, 2);
		
		stadium.addDestination(mountbatten, 2);
		mountbatten.addDestination(stadium, 2);
		
		mountbatten.addDestination(dakota, 2);
		dakota.addDestination(mountbatten, 2);
		
		dakota.addDestination(payaLebar, 2);
		payaLebar.addDestination(dakota, 2);
		
		payaLebar.addDestination(macpherson, 2);
		macpherson.addDestination(payaLebar, 2);
		
		macpherson.addDestination(taiSeng, 2);
		taiSeng.addDestination(macpherson, 2);
		
		taiSeng.addDestination(bartley, 2);
		bartley.addDestination(taiSeng, 2);
		
		bartley.addDestination(serangoon, 3);
		serangoon.addDestination(bartley, 3);
		
		serangoon.addDestination(lorongChuan, 2);
		lorongChuan.addDestination(serangoon, 2);
		
		lorongChuan.addDestination(bishan, 2);
		bishan.addDestination(lorongChuan, 2);
		
		bishan.addDestination(marymount, 3);
		marymount.addDestination(bishan, 3);
		
		marymount.addDestination(caldecott, 2);
		caldecott.addDestination(marymount, 2);
		
		caldecott.addDestination(botanicGardens, 5);
		botanicGardens.addDestination(caldecott, 5);
		
		botanicGardens.addDestination(farrerRoad, 2);
		farrerRoad.addDestination(botanicGardens, 2);
		
		farrerRoad.addDestination(hollandVillage, 3);
		hollandVillage.addDestination(farrerRoad, 3);
		
		hollandVillage.addDestination(buonaVista, 2);
		buonaVista.addDestination(hollandVillage, 2);
		
		buonaVista.addDestination(onenorth, 2);
		onenorth.addDestination(buonaVista, 2);
		
		onenorth.addDestination(kentRidge, 2);
		kentRidge.addDestination(onenorth, 2);
		
		kentRidge.addDestination(hawParVilla, 2);
		hawParVilla.addDestination(kentRidge, 2);
		
		hawParVilla.addDestination(pasirPanjang, 2);
		pasirPanjang.addDestination(hawParVilla, 2);
		
		pasirPanjang.addDestination(labradorPark, 3);
		labradorPark.addDestination(pasirPanjang, 3);
		
		labradorPark.addDestination(telokBlangah, 2);
		telokBlangah.addDestination(labradorPark, 2);
		
		telokBlangah.addDestination(harbourfront, 2);
		harbourfront.addDestination(telokBlangah, 2);
		
		Graph graph = new Graph();

		graph.addNode(harbourfront);
		graph.addNode(telokBlangah);
		graph.addNode(labradorPark);
		graph.addNode(pasirPanjang);
		graph.addNode(hawParVilla);
		graph.addNode(kentRidge);
		graph.addNode(onenorth);
		graph.addNode(hollandVillage);
		graph.addNode(farrerRoad);
		graph.addNode(botanicGardens);
		graph.addNode(caldecott);
		graph.addNode(marymount);
		graph.addNode(lorongChuan);
		graph.addNode(serangoon);
		graph.addNode(bartley);
		graph.addNode(taiSeng);
		graph.addNode(macpherson);
		graph.addNode(dakota);
		graph.addNode(mountbatten);
		graph.addNode(stadium);
		graph.addNode(nicollHighway);
		graph.addNode(promenade);
		graph.addNode(esplanade);
		graph.addNode(brasBasah);
		
		graph.addNode(tuasLink);
		graph.addNode(tuasWestRoad);
		graph.addNode(tuasCrescent);
		graph.addNode(gulCircle);
		graph.addNode(jooKoon);
		graph.addNode(pioneer);
		graph.addNode(boonLay);
		graph.addNode(lakeSide);
		graph.addNode(chineseGarden);
		graph.addNode(clementi);
		graph.addNode(dover);
		graph.addNode(buonaVista);
		graph.addNode(commonwealth);
		graph.addNode(queenstown);
		graph.addNode(redhill);
		graph.addNode(tiongBahru);
		graph.addNode(outramPark);
		graph.addNode(tanjongPagar);
		graph.addNode(bugis);
		graph.addNode(lavender);
		graph.addNode(kallang);
		graph.addNode(aljunied);
		graph.addNode(payaLebar);
		graph.addNode(eunos);
		graph.addNode(kembangan);
		graph.addNode(bedok);
		graph.addNode(tanahMerah);
		graph.addNode(simei);
		graph.addNode(tampines);
		graph.addNode(pasirRis);
		
		graph.addNode(marinaSouthPier);
		graph.addNode(marinaBay);
		graph.addNode(rafflesPlace);
		graph.addNode(cityHall);
		graph.addNode(dhobyGhaut);
		graph.addNode(somerset);
		graph.addNode(orchard);
		graph.addNode(newton);
		graph.addNode(novena);
		graph.addNode(toaPayoh);
		graph.addNode(braddell);
		graph.addNode(bishan);
		graph.addNode(angMoKio);
		graph.addNode(yioChuKang);
		graph.addNode(khatib);
		graph.addNode(yishun);
		graph.addNode(canberra);
		graph.addNode(sembawang);
		graph.addNode(admiralty);
		graph.addNode(woodlands);
		graph.addNode(marsiling);
		graph.addNode(kranji);
		graph.addNode(yewTee);
		graph.addNode(choaChuKang);
		graph.addNode(bukitGombak);
		graph.addNode(bukitBatok);
		graph.addNode(jurongEast);
		
		return graph;
	}
	
	public void updateGraphFromWebAPI() throws JSONException, IOException
	{
		URL url = new URL(NONOPERATIONALSTATIONS_URL);
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setRequestMethod("GET");
		urlConnection.setConnectTimeout(1000);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader
				(urlConnection.getInputStream()));
		String inputLine;
		StringBuilder content = new StringBuilder();
		while ((inputLine = bufferedReader.readLine()) != null)
		{
			content.append(inputLine);
		}
		bufferedReader.close();
		urlConnection.disconnect();

		JSONArray jsonArray = new JSONArray(content.toString());

		for (int i = 0; i < jsonArray.length(); i++)
		{
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String stationName = jsonObject.get("stationName").toString().trim();
			String stationCode = jsonObject.get("stationCode").toString();
			int timeToNextStation = Integer.parseInt(jsonObject.get("timeToNextStation")
					.toString());
			int timeToNextStationOpp = Integer.parseInt(jsonObject.get("timeToNextStationOpp")
					.toString());

			Node nodeToUpdate = this.findNode(stationName);
			List<Node> adjacentNodesToUpdate = nodeToUpdate.adjacentNodes.keySet().stream()
					.filter(x -> x.hasStationCodeStartingWith(stationCode.substring(0, 2)))
					.collect(Collectors.toList());

			if (adjacentNodesToUpdate.size() <= 1)
			{
				Node destination = adjacentNodesToUpdate.get(0);
				if (nodeToUpdate.isBeforeStationAndOnSameLine(destination))
					nodeToUpdate.addDestination(destination, timeToNextStationOpp);
				else
					nodeToUpdate.addDestination(destination, timeToNextStation);
			}
			else
			{
				Node destination1 = adjacentNodesToUpdate.get(0);
				if (nodeToUpdate.isBeforeStationAndOnSameLine(destination1))
				{
					nodeToUpdate.addDestination(destination1, timeToNextStationOpp);
					nodeToUpdate.addDestination(adjacentNodesToUpdate.get(1), timeToNextStation);
				}
				else
				{
					nodeToUpdate.addDestination(destination1, timeToNextStation);
					nodeToUpdate.addDestination(adjacentNodesToUpdate.get(1), timeToNextStationOpp);
				}
			}
		}
	}
}
