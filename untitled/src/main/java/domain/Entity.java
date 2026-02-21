package domain;

//Интерфейс сущность: Для игрока, монстров, предметов
public interface Entity {
    Position getPosition();

    void setPosition(Position position);
}
