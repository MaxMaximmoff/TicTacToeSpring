package com.maximoff.TicTacToeSpring.services;

import com.maximoff.TicTacToeSpring.exceptions.NotFoundException;
import com.maximoff.TicTacToeSpring.exceptions.InvalidGameException;
import com.maximoff.TicTacToeSpring.models.*;
import com.maximoff.TicTacToeSpring.storages.GameplayStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.maximoff.TicTacToeSpring.models.GameStatus.NEW;
import static com.maximoff.TicTacToeSpring.models.GameStatus.IN_PROGRESS;
import static com.maximoff.TicTacToeSpring.models.GameStatus.FINISHED;

import java.util.*;

@Service
@AllArgsConstructor
public class GameplayService {

    public Gameplay createGame(Player player1) {
        Gameplay gameplay = new Gameplay();
        // Создаем board[3][3] и записываем её в gameplay
        gameplay.setBoard(makeBoard(3));
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        // Добавляем в gameplay первого игрока
        gameplay.setPlayers(players);
        // Устанавливаем статус NEW
        gameplay.setStatus(NEW);

        Game game = new Game();

        gameplay.setGame(game);
        ArrayList<Step> steps = new ArrayList<Step>();
        gameplay.getGame().setSteps(steps);
        // Сохраняем gameplay в GameplayStorage
        GameplayStorage.getInstance().setGameplay(gameplay);
        return gameplay;
    }

    public Gameplay connectToGame(Player player2) throws NotFoundException, InvalidGameException {

        Gameplay gameplay = GameplayStorage.getInstance().getGameplay();

        // Исключение, если предварительно не был запущен createGame
        if (gameplay.getPlayers() == null ) {
            throw new NotFoundException("Игра не была создана ранее!");
        }

        // Исключение при попытке добавить третьего игрока
        if (gameplay.getPlayers().size() == 2 && gameplay.getPlayers().get(1) != null) {
            throw new InvalidGameException("Набор игроков полон!");
        }

        // Исключение при попытке добавить игрока с одинаковым именем
        if (player2.getPlayerName().equals(gameplay.getPlayers().get(0).getPlayerName())) {
            throw new InvalidGameException("Одинаковые имена игроков недопустимы!");
        }

        // Добавляем второго игрока в gameplay
        gameplay.getPlayers().add(player2);
        // Выбор в случайном порядке кто будет ходить первым
        ArrayList<Player> playersOrdered = chooseFirst(gameplay.getPlayers());
        gameplay.setPlayers(playersOrdered);
        gameplay.setCurPlayer(playersOrdered.get(0));
        // Устанавливаем статус IN_PROGRESS
        gameplay.setStatus(IN_PROGRESS);
        GameplayStorage.getInstance().setGameplay(gameplay);
        return gameplay;
    }

    public Gameplay makeStep(Step step) throws NotFoundException, InvalidGameException {

        Gameplay gameplay = GameplayStorage.getInstance().getGameplay();

        // Исключение, если предварительно не был запущен createGame
        if (gameplay.getPlayers() == null ) {
            throw new NotFoundException("Игра не была создана ранее!");
        }

        // Исключение, если предварительно не был запущен connectToGame
        if (gameplay.getPlayers().size() == 1) {
            throw new InvalidGameException("Второй игрок не добавлен!");
        }

        // Исключение если игра уже завершена
        if (gameplay.getStatus().equals(FINISHED)) {
            throw new InvalidGameException("Игра уже закончилась!");
        }

        gameplay.getGame().getSteps().add(step);

        String[][] board = gameplay.getBoard();

        String symbol = gameplay.getCurPlayer().getPlayerMark();

        Player current_player = gameplay.getCurPlayer();

        gameplay.setBoard(placeSymbol(board, symbol, step.getStepValue(), 3));


        if(checkGameFinish(gameplay.getBoard(), current_player)) {

            if(fullBoardCheck(board)) {
                gameplay.setGameResult(new GameResult());
            } else {
                gameplay.setGameResult(new GameResult(current_player));
            }
            gameplay.setStatus(FINISHED);
        } else {
            gameplay.setCurPlayer(switchPlayer(gameplay.getPlayers(), current_player));
        }

        return gameplay;
    }

    public Gameplay getGamplay(String text) throws NotFoundException, InvalidGameException {

        Gameplay gameplay = GameplayStorage.getInstance().getGameplay();

        // Исключение, если предварительно не был запущен createGame
        if (gameplay.getPlayers() == null ) {
            throw new NotFoundException("Игра не была создана ранее!");
        }

        // Исключение, если предварительно не был запущен connectToGame
        if (gameplay.getPlayers().size() == 1) {
            throw new InvalidGameException("Второй игрок не добавлен!");
        }

        // Исключение если игра уже завершена
        if (gameplay.getStatus().equals(FINISHED)) {
            throw new InvalidGameException("Игра уже закончилась!");
        }

        Player current_player = gameplay.getCurPlayer();

        String num = "";
        int steps_size = gameplay.getGame().getSteps().size();

        if(gameplay.getGame().getSteps().size() == 0) {
            num = "1";
        } else {
            num = Integer.toString(Integer.parseInt(gameplay.getGame().getSteps().get(steps_size - 1).getNum()) + 1);
        }
        Step step = new Step();
        step.setNum(num);
        step.setPlayerId(current_player.getPlayerID());
        step.setStepValue(text);

        gameplay.getGame().getSteps().add(step);

        String[][] board = gameplay.getBoard();

        String symbol = current_player.getPlayerMark();

        gameplay.setBoard(placeSymbol1(board, symbol, text, 3));

        if(checkGameFinish(gameplay.getBoard(), current_player)) {

            if(fullBoardCheck(board)) {
                gameplay.setGameResult(new GameResult());
            } else {
                gameplay.setGameResult(new GameResult(current_player));
            }
            gameplay.setStatus(FINISHED);
//            gameplay.setCurPlayer(null);
        } else {
            gameplay.setCurPlayer(switchPlayer(gameplay.getPlayers(), current_player));
        }

        return gameplay;
    }

    public Json getJsonStor(String json_type) throws NotFoundException, InvalidGameException {

        Gameplay gameplayStor = GameplayStorage.getInstance().getGameplay();
        ArrayList<Player> players = new ArrayList<>();
        Game game = new Game();
        Player winner = new Player();

        if (gameplayStor.getPlayers() != null) {
            players = gameplayStor.getPlayers();
        }

        if (gameplayStor.getGame() != null) {
            game = gameplayStor.getGame();
        }

        if (gameplayStor.getGameResult() != null) {
            winner = gameplayStor.getGameResult().getPlayer();
        }

        Gameplay gameplay = new Gameplay();
        Json json = new Json(gameplay);

        if (json_type.equals("1")){
            gameplay.setPlayers(players);
            gameplay.setGame(game);
            gameplay.setGameResult(new GameResult(winner));
            json.setGameplay(gameplay);
        }

        return json;
    }

    // Метод выбора в случайном порядке кто будет ходить первым
    private ArrayList<Player> chooseFirst(ArrayList<Player> players) {

        Random r = new Random();
        Player firstPlayer = players.get(r.nextInt(players.size()));
        System.out.printf("Игрок %s ходит первым!\n", firstPlayer.getPlayerName());

        ArrayList<Player> playersOrdered = new ArrayList<Player>();
        playersOrdered.add(firstPlayer);
        players.forEach((entry) -> {
            if(!entry.equals(firstPlayer)){
                playersOrdered.add(entry);
            }
        });
        // Перенумеровываем заново. Игрок с PlayerID="1" ходит первым
        playersOrdered.get(0).setPlayerID("1");
        playersOrdered.get(1).setPlayerID("2");
        return playersOrdered;
    }

    // Создание двумерного игрового поля
    private String[][] makeBoard(int size) {

        String[][] matrix = new String[size][size];

        for (int row=0; row<size; row++) {
            for (int col=0; col<size; col++) {
                if (matrix[row][col]==null) {
                    matrix[row][col] = "";
                }
            }
        }
        return matrix;
    }

    // Создание двумерного игрового поля c заполненными цифрами
    private String[][] makeNbredBoard(int size) {

        String[][] matrix = new String[size][size];
        int i = 1;
        for (int row=0; row<size; row++) {
            for (int col=0; col<size; col++) {
                if (matrix[row][col]==null) {
                    matrix[row][col] = Integer.toString(i);
                }
                i++;
            }
        }
        return matrix;
    }

    // Установка маркера игрока в указанную позицию
    private String[][] placeSymbol(String[][] board, String symbol, String position, int size) {

        int pos_int = Integer.parseInt(position);

        int[] pos = new int[]{0, 0};
        int i = 1;
        for (int row=0; row<size; row++) {
            for (int col=0; col<size; col++) {
                if (i==pos_int) {
                    pos[1] = row;
                    pos[0] = col;
                }
                i++;
            }
        }

        board[pos[1]][pos[0]] = symbol;
        return board;
    }

    // Установка маркера игрока в указанную позицию
    private String[][] placeSymbol1(String[][] board, String symbol, String position, int size) {

        int pos_int = Integer.parseInt(position);

        int[] pos = new int[]{0, 0};
        int i = 1;
        for (int row=0; row<size; row++) {
            for (int col=0; col<size; col++) {
                if (i==pos_int) {
                    pos[1] = row;
                    pos[0] = col;
                }
                i++;
            }
        }

        board[pos[1]][pos[0]] = symbol;
        return board;
    }

    // Переключение игрока при смены очереди хода
    private Player switchPlayer(List<Player> players, Player current_player) {

        if(players.get(0).equals(current_player)){
            return players.get(1);
        }
        return players.get(0);
    }

    // Проверка того, завершена ли игра
    private boolean checkGameFinish(String[][] board, Player current_player) {
        if (this.winСheck(board, current_player.getPlayerMark())) {
            System.out.printf("Игрок %s (играл за %s) выиграл!\n",
                    current_player.getPlayerName(), current_player.getPlayerMark());
            return true;
        }
        if (this.fullBoardCheck(board)) {
            System.out.println("Игра завершилась вничью!\n");
            return true;
        }
        return false;
    }

    // Проверка выиграл ли игрок с указанным маркером игру
    private boolean winСheck(String[][] matrix, String mark) {

        int l = matrix.length;

        String[] filled_line = new String[]{mark, mark, mark};

        if(Arrays.equals(getDiagonal1(matrix), filled_line)) {
            return true;
        }
        if(Arrays.equals(getDiagonal2(matrix), filled_line)) {
            return true;
        }
        for (int i=0; i<l; i++) {

            if(Arrays.equals(getRow(matrix, i), filled_line) ||
                    Arrays.equals(getColumn(matrix, i), filled_line)) {
                return true;
            }
        }
        return false;
    }

    //Определяет заполнена ли доска полностью маркерами X и O
    private boolean fullBoardCheck(String[][] matrix){

        Set<String> board = new HashSet<String>();

        String[] oneDArray = new String[matrix.length * matrix.length];
        for(int i = 0; i < matrix.length; i++)
        {
            for(int s = 0; s < matrix.length; s++)
            {
                oneDArray[(i * matrix.length) + s] = matrix[i][s];
            }
        }

        board.addAll(Arrays.asList(oneDArray));

        if (board.size()==2 && board.contains("X") && board.contains("O")
                && (!winСheck(matrix, "X") && !winСheck(matrix, "O")))
            return true;

        return false;

    }

    // Получение столбца матрицы
    private String[] getColumn(String[][] array, int index){
        String[] column = new String[array[0].length];
        for(int i=0; i<column.length; i++){
            column[i] = array[i][index];
        }
        return column;
    }

    // Получение ряда матрицы
    private String[] getRow(String[][] array, int index){
        String[] row = new String[array[0].length];
        for(int i=0; i<row.length; i++){
            row[i] = array[index][i];
        }
        return row;
    }

    // Получение первой главной диагонали матрицы
    private String[] getDiagonal1(String[][] array){
        String[] diagonal = new String[array[0].length];
        for(int i=0; i<diagonal.length; i++){
            diagonal[i] = array[i][i];
        }
        return diagonal;
    }

    // Получение второй главной диагонали матрицы
    private String[] getDiagonal2(String[][] array){
        String[] diagonal = new String[array[0].length];
        for(int i=0; i<diagonal.length; i++){
            diagonal[i] = array[array[0].length-1-i][i];
        }
        return diagonal;
    }


}
