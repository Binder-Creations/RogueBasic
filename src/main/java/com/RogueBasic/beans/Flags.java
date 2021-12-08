package com.RogueBasic.beans;

import java.util.Objects;

import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@UserDefinedType(value = "flags")
public class Flags {
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
	
	public Flags(String flag){
		switch(flag) {
			case "magic":
				this.magic = -1;
				break;
			case "critDoubleDamage":
				this.critDoubleDamage = -1;
				break;
			case "crit":
				this.crit = -1;
				break;
			case "dodge":
				this.dodge = -1;
				break;
			case "highPen":
				this.highPen = -1;
				break;
			case "hit":
				this.hit = -1;
				break;
			case "fullPen":
				this.fullPen = -1;
				break;
			case "stun":
				this.stun = 1;
				break;
		}
	}
	
	public int isCritDoubleDamage() {
		return critDoubleDamage;
	}
	public void setCritDoubleDamage(int critDoubleDamage) {
		this.critDoubleDamage = critDoubleDamage;
	}
	public int isCrit() {
		return crit;
	}
	public void setCrit(int crit) {
		this.crit = crit;
	}
	public int isDodge() {
		return dodge;
	}
	public void setDodge(int dodge) {
		this.dodge = dodge;
	}
	public int isHighPen() {
		return highPen;
	}
	public void setHighPen(int highPen) {
		this.highPen = highPen;
	}
	public int isHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public int isFullPen() {
		return fullPen;
	}
	public void setFullPen(int fullPen) {
		this.fullPen = fullPen;
	}
	public int isStun() {
		return stun;
	}
	public void setStun(int stun) {
		this.stun = stun;
	}
	public int isFortress() {
		return fortress;
	}
	public void setFortress(int fortress) {
		this.fortress = fortress;
	}
	public int isMagic() {
		return magic;
	}
	public void setMagic(int magic) {
		this.magic = magic;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(crit, critDoubleDamage, dodge, fortress, fullPen, highPen, hit, magic, stun);
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
		return crit == other.crit && critDoubleDamage == other.critDoubleDamage && dodge == other.dodge
				&& fortress == other.fortress && fullPen == other.fullPen && highPen == other.highPen
				&& hit == other.hit && magic == other.magic && stun == other.stun;
	}
	
	@Override
	public String toString() {
		return "Flags [critDoubleDamage=" + critDoubleDamage + ", crit=" + crit + ", dodge=" + dodge + ", highPen="
				+ highPen + ", hit=" + hit + ", fullPen=" + fullPen + ", stun=" + stun + ", fortress=" + fortress
				+ ", magic=" + magic + "]";
	}

}
