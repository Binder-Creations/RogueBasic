import CombatUpdate from "../CombatUpdate";
import AbilityServices from "../AbilityServices";

class MonsterAttack extends AbilityServices {
  static use(pc, monsters, monster, combatUpdates){
    let damage = AbilityServices.calcDamageMonster(AbilityServices.randomize(monster.baseDamage, 25), pc, monster);
    pc.currentHealth = (pc.currentHealth - damage) >= 0 ? (pc.currentHealth - damage) : 0;
    combatUpdates.push(new CombatUpdate("pc", "dmg-"+damage));
  }
}

export default MonsterAttack;