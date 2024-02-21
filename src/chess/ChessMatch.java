package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	
	private int turn;
	private Color currentPlayer;
	
	//Declaração de listas 
	//Lista de peças no tabuleiro
	private List<Piece> piecesOnTheBoard = new ArrayList<Piece>();
	//Lista de peças capturadas
	private List<Piece> capturedPieces = new ArrayList<Piece>();
	

	//Importar tabuleiro
	private Board board;
	
	//Para verificar se a partida está em posição de check ou não
	private boolean check;
	
	//Criar jogo
	public ChessMatch() {
		//Tamanho do tabuleiro
		board = new Board(8, 8);
		//Turno do jogador
		turn = 1;
		//Cor do jogador inicial
		currentPlayer = Color.WHITE;
		//Peças no tabuleiro
		initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}

	public boolean getCheck() {
		return check;
	}


	//Retorna uma matriz com peças de xadrez corresponente a uma partida
	public ChessPiece[][] getPieces() {
		//Matriz de peças de xadrez
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColums()];
		//Percorre a matriz do tabuleiro e coloca as posições do tabuleiro
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColums(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition){
		//Coverter posição de Xadrez para uma posição de matriz
		Position position = sourcePosition.toPosition();
		//Validar posição de origem
		validateSourcePosition(position);
		//Retornar os movimentos possiveis da peça dessa posição
		return board.piece(position).possibleMoves();
	}
	
	//Executa o movimento de uma peça
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		//Posição de origem
		Position source = sourcePosition.toPosition();
		//Posição de destino
		Position target = targetPosition.toPosition();
		//Validação da posição de origem
		validateSourcePosition(source);
		//Validação da posição de destino
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);
		
		//Verificar se o movimento colocou o próprio jogador em check
		if (testCheck(currentPlayer)) {
			//Desfazer o movimento
			undoMove(source, target, capturedPiece);
			
			throw new ChessException("You can't put yourself in check");
		}
		
		//Verificar se o oponente se colocou em check
		if (check = testCheck(opponent(currentPlayer))) {
			check = true;
		}
		else {
			check =  false;
		}
		nextTurn();
		return (ChessPiece) capturedPiece;
	}
	
	//Fazer movimento da peça
	private Piece makeMove(Position source, Position target) {
		//remover a peça da posição de origem
		Piece p = board.removePiece(source);
		//remover a possivel peça que esteja na posição de destino e vai ser a peça capturada
		Piece capturedPiece = board.removePiece(target);
		//Colocar a posição que estava na origem na posição de destino
		board.placePiece(p, target);
		
		//Se a peça capturada for diferente de nulo captura-se uma peça
		if (capturedPiece != null) {
			//Remover a peça da lista de tabuleiro
			piecesOnTheBoard.remove(capturedPiece);
			//Adicionar peça na lista de peças capturadas
			capturedPieces.add(capturedPiece);
		}
		return capturedPiece;
	}
	
	//Desfazer o movimento da peça
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		//Remove a peça movida no destino
		Piece p = board.removePiece(target);
		//Devolver à posição de origem
		board.placePiece(p, source);
		
		//Voltar a colocar a peça capturada na posição de destino
		if (capturedPiece != null) {
			//Colocar a peça na posição de destino
			board.placePiece(capturedPiece, target);
			
			//Retirar peça da lista de peças capturadas e colocar na lista de peças no tabuleiro
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
			
		}
	}
	
	//Validação da posição de origem
	private void validateSourcePosition(Position position) {
		//Se não existir um apeça nesta posição
		if (!board.thereIsApiece(position)) {
			throw new ChessException("There is no piece on source position");
		}
		
		//Verifcar se cor é diferente do jogador atual (peça do adversário)
		if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) {
			throw new ChessException("the chosen piece is not yours");
		}
		
		//Verifcar se existe movimentos possiveis para a peça, se não existir movimentos posssiveis para as peças tambem nao se pode utlizar essa peça como origem
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}
	
	//Validação da posição de destino
	private void validateTargetPosition(Position source, Position target) {
		//Se para a peça de origem, a posição de destino não é um movimento possível, não é possível mexer para o destino
		if (!board.piece(source).possibleMoves(target)) {
			throw new ChessException("The chosen piece can't move to target position");
		}
	}
	
	//Incrementar os turnos das jogadas
	private void nextTurn() {
		turn++;
		if (currentPlayer == Color.WHITE) {
			currentPlayer = Color.BLACK;
		}else {
			currentPlayer = Color.WHITE;
		}
	}
	
	//Devolve um opponent de uma cor
	private Color opponent(Color color) {
		if (color == Color.WHITE) {
			color = Color.BLACK;
		}
		else {
			color = Color.WHITE;
		}
		return color;
	}
	
	//Para localizar um rei de uma determinada cor
	private ChessPiece king(Color color) {
		//Procurar na lista de peças em jogo qual é que é o rei dessa cor
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color ).collect(Collectors.toList());
		
		//Para cada peça 'piece' na lista 'list'  
		for (Piece piece : list) {
			
			//Se a lista 'piece' for uma instancia King significa que se encotrou o rei
			if (piece instanceof King) {
				return (ChessPiece) piece;
			}
		}
		throw new IllegalStateException("There is no " + color + " king on the board");
	}
	
	//Para testar se um rei de uma terminada cor esta em check
	private boolean testCheck(Color color) {
		//Ver a posição do rei no formato de matriz
		Position kingPosition = king(color).getChessPosition().toPosition();
		
		//Lista das peças do oponente dessa cor - peças no tabuleiro filtradas com a cor do oponente desse rei
		List<Piece> opponentePieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		
		//Para cada peça 'piece' na lista opponentPieces testar se existe algum movimento possivel dessa peça que leva à posição do rei
		for (Piece piece : opponentePieces) {
			//Matriz de movimentos possiveis da peça adversária
			boolean[][] mat = piece.possibleMoves();
			
			//Se nesta matriz a posição correspondente à posição do rei for true significa que o rei está em check
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}

	//Colocar nova peça com as posições certas a1,a2...em vez de (0,0),(0,1), etc...
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		
		//Colocar a peça dentro da lista de peças no tabuleiro
		piecesOnTheBoard.add(piece);
	}
	
	//Colocar peças no tabuleiro
	private void initialSetup() {
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
}
