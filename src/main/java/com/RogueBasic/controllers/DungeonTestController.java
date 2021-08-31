package com.RogueBasic.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.RogueBasic.beans.Dungeon;
import com.RogueBasic.beans.Floor;
import com.RogueBasic.beans.Player;
import com.RogueBasic.beans.PlayerCharacter;
import com.RogueBasic.beans.Room;
import com.RogueBasic.data.DungeonDao;
import com.RogueBasic.data.EquipmentDao;
import com.RogueBasic.data.FloorDao;
import com.RogueBasic.data.ItemDao;
import com.RogueBasic.data.MonsterDao;
import com.RogueBasic.data.PlayerCharacterDao;
import com.RogueBasic.data.PlayerDao;
import com.RogueBasic.data.RoomDao;
import com.RogueBasic.data.TrapDao;
import com.RogueBasic.services.DungeonServices;
import com.RogueBasic.util.CassandraConnector;
import com.RogueBasic.util.CassandraUtilities;
import com.datastax.oss.driver.api.core.CqlSession;

@Controller
//@RestController
public class DungeonTestController {
	@GetMapping("/dungeon_test")
	public String dungeonTest(Model model) {
		try {
			CqlSession session = CassandraConnector.getSession();
			CassandraUtilities cu = new CassandraUtilities(session);
			DungeonServices ds = new DungeonServices(session);
			PlayerCharacter pc = new PlayerCharacter("taco", "Warrior", 1, 1, 1, 1, 0);
			PlayerCharacterDao pcd = new PlayerCharacterDao(session);
			DungeonDao dd = new DungeonDao(session);
			FloorDao fd = new FloorDao(session);
			RoomDao rdao = new RoomDao(session);
			MonsterDao mdao = new MonsterDao(session);
			EquipmentDao edao = new EquipmentDao(session);
			ItemDao idao = new ItemDao(session);
			TrapDao tdao = new TrapDao(session);
			pcd.save(pc);
			Dungeon d = dd.findById(ds.addFloors(ds.generateShell(pc.getId())).getId());
			model.addAttribute("dungeonName", d.getName());
			model.addAttribute("dungeonDescription", d.getDescription());
//			List<String> sb = new ArrayList<>();
//			sb.add(d.toString());
//			for(UUID id: d.getFloorIds()) {
//				Floor floor = fd.findById(id);
//				sb.add(floor.toString());
//				for(UUID rid: floor.getRoomIds()) {
//					Room room = rdao.findById(rid);
//					sb.add(room.toString());
//					if(room.getMonsterIds()!=null) {
//						for(UUID u : room.getMonsterIds()) {
//							sb.add(mdao.findById(u).toString());
//						}
//					}
//					if(room.getItemIds()!=null) {
//						for(UUID u : room.getItemIds()) {
//							sb.add(idao.findById(u).toString());
//						}
//					}
//					if(room.getEquipmentIds()!=null) {
//						for(UUID u : room.getEquipmentIds()) {
//							sb.add(edao.findById(u).toString());
//						}
//					}
//					if(room.getTrapId()!=null) {
//						sb.add(tdao.findById(room.getTrapId()).toString());
//					}
//				}
//			}
			return "dungeon";
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
