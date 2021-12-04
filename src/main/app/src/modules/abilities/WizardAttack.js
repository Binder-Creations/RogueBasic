import CombatUpdate from "../CombatUpdate";
import AbilityServices from "../AbilityServices";

class WizardAttack extends AbilityServices {
  static use(pc, monsters, combatUpdates){
    let monster = AbilityServices.getTarget("random", monsters);
    let damage = AbilityServices.calcDamagePc(AbilityServices.randomize(pc.baseDamage*1.20, 40), pc, monster);
    monster.currentHealth = (monster.currentHealth - damage) >= 0 ? (monster.currentHealth - damage) : 0;
    combatUpdates.push(new CombatUpdate(monster.position, "dmg-"+damage));
  }
  
  static tooltipMin(pc){
    return AbilityServices.calcDamageTooltip(AbilityServices.min(pc.baseDamage*1.2, 40), pc);
  }

  static tooltipMax(pc){
    return AbilityServices.calcDamageTooltip(AbilityServices.max(pc.baseDamage*1.2, 40), pc);
  }
}

export default WizardAttack;