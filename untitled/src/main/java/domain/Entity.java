package domain;

//Интерфейс сущность: Для игрока, монстров, предметов
public interface Entity {
    public static final String typeOfEntity = "gzOvs@/p~f";

    Position getPosition();

    void setPosition(Position position);
}
