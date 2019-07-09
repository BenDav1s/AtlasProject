package spells;

public enum SpellType {
	Attack,Attack_All,
	Heal, Heal_ALL,
	Shield, Shield_ALL,
	Blade,Blade_ALL,
	Trap,Trap_ALL;
	public static SpellType getSpellType(String id) {
		if(id.equalsIgnoreCase("Attack")) return SpellType.Attack;
		if(id.equalsIgnoreCase("Attack_All")) return SpellType.Attack_All;
		if(id.equalsIgnoreCase("Heal")) return SpellType.Heal;
		if(id.equalsIgnoreCase("Heal_ALL")) return SpellType.Heal_ALL;
		if(id.equalsIgnoreCase("Shield")) return SpellType.Shield;
		if(id.equalsIgnoreCase("Shield_All")) return SpellType.Shield_ALL;
		if(id.equalsIgnoreCase("Blade")) return SpellType.Blade;
		if(id.equalsIgnoreCase("Blade_ALL")) return SpellType.Blade_ALL;
		if(id.equalsIgnoreCase("Trap")) return SpellType.Trap;
		if(id.equalsIgnoreCase("Trap_ALL")) return SpellType.Trap_ALL;
		
		
		return null;
	}
}
