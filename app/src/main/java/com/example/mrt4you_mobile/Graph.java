package com.example.mrt4you_mobile;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
		Optional<Node> node = nodes.stream().filter(x -> x.getName().equals(nodeName)).findFirst();
		return node.isPresent() ? node.get() : null;
	}
	
	public static Graph createMRTGraph()
	{
		Node jurongEast = new Node("Jurong East");
		Node bukitBatok = new Node("Bukit Batok");
		Node bukitGombak = new Node("Bukit Gombak");
		Node choaChuKang = new Node("Choa Chu Kang");
		Node yewTee = new Node("Yew Tee");
		Node kranji = new Node("Kranji");
		Node marsiling = new Node("Marsiling");
		Node woodlands = new Node("Woodlands");
		Node admiralty = new Node("Admiralty");
		Node sembawang = new Node("Sembawang");
		Node canberra = new Node("Canberra");
		Node yishun = new Node("Yishun");
		Node khatib = new Node("Khatib");
		Node yioChuKang = new Node("Yio Chu Kang");
		Node angMoKio = new Node("Ang Mo Kio");
		Node bishan = new Node("Bishan");
		Node braddell = new Node("Bradell");
		Node toaPayoh = new Node("Toa Payoh");
		Node novena = new Node("Novena");
		Node newton = new Node("Newton");
		Node orchard = new Node("Orchard");
		Node somerset = new Node("Somerset");
		Node dhobyGhaut = new Node("Dhoby Ghaut");
		Node cityHall = new Node("City Hall");
		Node rafflesPlace = new Node("Raffles Place");
		Node marinaBay = new Node("Marina Bay");
		Node marinaSouthPier = new Node("Marina South Pier");
		
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
		Node tampines = new Node("Tampines");
		Node simei = new Node("Simei");
		Node tanahMerah = new Node("Tanah Merah");
		Node bedok = new Node("Bedok");
		Node kembangan = new Node("Kembangan");
		Node eunos = new Node("Eunos");
		Node payaLebar = new Node("Paya Lebar");
		Node aljunied = new Node("Aljunied");
		Node kallang = new Node("Kallang");
		Node lavender = new Node("Lavender");
		Node bugis = new Node("Bugis");
		Node tanjongPagar = new Node("Tanjong Pagar");
		Node outramPark = new Node("Outram Park");
		Node tiongBahru = new Node("Tiong Bahru");
		Node redhill = new Node("Redhill");
		Node queenstown = new Node("Queenstown");
		Node commonwealth = new Node("Commonwealth");
		Node buonaVista = new Node("Buona Vista");
		Node dover = new Node("Dover");
		Node clementi = new Node("Clementi");
		Node chineseGarden = new Node("Chinese Garden");
		Node lakeSide = new Node("Lakeside");
		Node boonLay = new Node("Boon Lay");
		Node pioneer = new Node("Pioneer");
		Node jooKoon = new Node("Joo Koon");
		Node gulCircle = new Node("Gul Circle");
		Node tuasCrescent = new Node("Tuas Crescent");
		Node tuasWestRoad = new Node("Tuas West Road");
		Node tuasLink = new Node("Tuas Link");
		
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
		Node esplanade = new Node("Esplanade");
		Node promenade = new Node("Promenade");
		Node nicollHighway = new Node("Nicoll Highway");
		Node stadium = new Node("Stadium");
		Node mountbatten = new Node("Mountbatten");
		Node dakota = new Node("Dakota");
		Node macpherson = new Node("MacPherson");
		Node taiSeng = new Node("Tai Seng");
		Node bartley = new Node("Bartley");
		Node serangoon = new Node("Serangoon");
		Node lorongChuan = new Node("Lorong Chuan");
		Node marymount = new Node("Marymount");
		Node caldecott = new Node("Caldecott");
		Node botanicGardens = new Node("Botanic Gardens");
		Node farrerRoad = new Node("Farrer Road");
		Node hollandVillage = new Node("Holland Village");
		Node onenorth = new Node("one-north");
		Node kentRidge = new Node("Kent Ridge");
		Node hawParVilla = new Node("Haw Par Villa");
		Node pasirPanjang = new Node("Pasir Panjang");
		Node labradorPark = new Node("Labrador Park");
		Node telokBlangah = new Node("Telok Blangah");
		Node habourfront = new Node("HarbourFront");
		Node bayfront = new Node("Bayfront");
		
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
		
		telokBlangah.addDestination(habourfront, 2);
		habourfront.addDestination(telokBlangah, 2);
		
		Graph graph = new Graph();
		
		graph.addNode(bayfront);
		graph.addNode(habourfront);
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
}
