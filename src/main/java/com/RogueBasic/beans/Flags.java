package com.RogueBasic.beans;

import java.util.Objects;

import org.springframework.data.cassandra.core.mapping.UserDefinedType;

public class Flags {
	private int attackEnergy;
	private int critDoubleDamage;
	private int crit;
	private int dodge;
	private int highPen;
	private int hit;
	private int fullPen;
	private int stun;
	private int fortress;
	private int magic;
	
	public Flags() {}
	
	public Flags(String flag, int value){
		switch(flag) {
			case "magic":
				this.magic = value;
				break;
			case "attackEnergy":
				this.attackEnergy = value;
				break;
			case "critDoubleDamage":
				this.critDoubleDamage = value;
				break;
			case "crit":
				this.crit = value;
				break;
			case "dodge":
				this.dodge = value;
				break;
			case "highPen":
				this.highPen = value;
				break;
			case "hit":
				this.hit = value;
				break;
			case "fullPen":
				this.fullPen = value;
				break;
			case "stun":
				this.stun = value;
				break;
		}
	}
	
	public Flags(String flag) {
		this(flag, 1);
	}
	
	public int getAttackEnergy() {
		return attackEnergy;
	}

	public void setAttackEnergy(int attackEnergy) {
		this.attackEnergy = attackEnergy;
	}

	public int getCritDoubleDamage() {
		return critDoubleDamage;
	}

	public void setCritDoubleDamage(int critDoubleDamage) {
		this.critDoubleDamage = critDoubleDamage;
	}

	public int getCrit() {
		return crit;
	}

	public void setCrit(int crit) {
		this.crit = crit;
	}

	public int getDodge() {
		return dodge;
	}

	public void setDodge(int dodge) {
		this.dodge = dodge;
	}

	public int getHighPen() {
		return highPen;
	}

	public void setHighPen(int highPen) {
		this.highPen = highPen;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public int getFullPen() {
		return fullPen;
	}

	public void setFullPen(int fullPen) {
		this.fullPen = fullPen;
	}

	public int getStun() {
		return stun;
	}

	public void setStun(int stun) {
		this.stun = stun;
	}

	public int getFortress() {
		return fortress;
	}

	public void setFortress(int fortress) {
		this.fortress = fortress;
	}

	public int getMagic() {
		return magic;
	}

	public void setMagic(int magic) {
		this.magic = magic;
	}

	@Override
	public int hashCode() {
		return Objects.hash(attackEnergy, crit, critDoubleDamage, dodge, fortress, fullPen, highPen, hit, magic, stun);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Flags other = (Flags) obj;
		return attackEnergy == other.attackEnergy && crit == other.crit && critDoubleDamage == other.critDoubleDamage
				&& dodge == other.dodge && fortress == other.fortress && fullPen == other.fullPen
				&& highPen == other.highPen && hit == other.hit && magic == other.magic && stun == other.stun;
	}
	
	@Override
	public String toString() {
		return "Flags [attackEnergy=" + attackEnergy + ", critDoubleDamage=" + critDoubleDamage + ", crit=" + crit
				+ ", dodge=" + dodge + ", highPen=" + highPen + ", hit=" + hit + ", fullPen=" + fullPen + ", stun="
				+ stun + ", fortress=" + fortress + ", magic=" + magic + "]";
	}

}
