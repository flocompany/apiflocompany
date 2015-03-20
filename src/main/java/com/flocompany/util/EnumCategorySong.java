package com.flocompany.util;

public enum EnumCategorySong {

	NONE("none", "None"),ALL("all", "ALL"), FRENCH_MOVIE("french_movie", "French Movie"), US_MOVIE(
			"us_movie", "US Movie"), MUSIC("music", "Music");

	private String code = "";
	private String libelle = "";

	EnumCategorySong(String code, String libelle) {
		this.code = code;
		this.libelle = libelle;
	}

	public static EnumCategorySong get(String code) {
		if(ALL.getCode().equals(code)){
			return ALL;
		}
		if(FRENCH_MOVIE.getCode().equals(code)){
			return FRENCH_MOVIE;
		}
		if(US_MOVIE.getCode().equals(code)){
			return US_MOVIE;
		}
		if(MUSIC.getCode().equals(code)){
			return MUSIC;
		}
		return NONE;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

}
