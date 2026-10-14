package foundustry.types;

public enum Category {
    LOGISTICS("Логистика"),
    PRODUCTION("Производство"),
    POWER("Энергетика"),
    SCIENCE("Наука"),
    EQUIPMENT("Экипировка"),
    DEFENCE("Защита");

    public final String localizedName;

    Category(String localizedName) {
        this.localizedName = localizedName;
    }
}
