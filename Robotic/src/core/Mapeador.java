package core;

import java.util.ArrayList;
import java.util.List;

import algorithms.Edge;
import algorithms.Vertex;

public class Mapeador {
	static List<Vertex> nodes = new ArrayList<>();
	static List<Edge> edges = new ArrayList<>();

	static int[][] world = new int[][] {
		{0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 0, 0}
	};
	
	enum Direcao {
		CIMA,
		BAIXO,
		ESQUERDA,
		DIREITA
	}
	
	static class Robo {
		private int cycles = 1900;
		private int speed = 390;
		private int rotate = 340;

		private int posicaoX = 0;
		private int posicaoY = 0;
		
		Direcao direcao = Direcao.DIREITA;
		
		private void velocidade(int speed) {
			//Motor.C.setSpeed(speed);
			//Motor.B.setSpeed(speed);
		}
		
		public boolean detectaParedes() {
			return true;
		}
		
		public void posicaoAtualEhVertice() {
			
		}

		public void rotacionaDireita() {
			this.velocidade(speed);
			
			//Motor.C.rotate(rotate, true);
			//Motor.B.rotate(-rotate);
			
			this.velocidade(0);
			
			if (direcao == Direcao.DIREITA) {
				direcao = Direcao.BAIXO;
			} else if (direcao == Direcao.BAIXO) {
				direcao = Direcao.ESQUERDA;
			} else if (direcao == Direcao.ESQUERDA) {
				direcao = Direcao.CIMA;
			} else if (direcao == Direcao.CIMA) {
				direcao = Direcao.DIREITA;
			}
		}
		
//		public void rotacionaEsquerda() {
//			this.velocidade(speed);
//			
//			Motor.C.rotate(-rotate, true);
//			Motor.B.rotate(rotate);
//			
//			this.velocidade(0);
//		}
		
		public void movimenta() {
			this.velocidade(speed);
			
			for (int i = 0; i < cycles; i++) {
				//Motor.B.forward();
				//Motor.C.forward();
			}

			this.velocidade(0);
			
			world[posicaoY][posicaoX] = 1;

			this.posicaoX = this.proximoX();
			this.posicaoY = this.proximoY();
		}
		
		private int proximoX() {
			if (direcao == Direcao.DIREITA) {
				return this.posicaoX + 1;
			}
			
			if (direcao == Direcao.ESQUERDA) {
				return this.posicaoX - 1;
			}
			
			return this.posicaoX;
		}
		
		private int proximoY() {
			if (direcao == Direcao.BAIXO) {
				return this.posicaoY + 1;
			}
			
			if (direcao == Direcao.CIMA) {
				return this.posicaoY - 1;
			}
			
			return this.posicaoY;
		}
		
		public boolean podeMovimentar() {
			return !this.temParede() && !this.proximoInvalido() && !this.proximoMapeado();
		}
		
		private boolean proximoInvalido() {
			return (
				this.proximoX() == -1 ||
				this.proximoX() == world.length ||
				this.proximoY() == -1 ||
				this.proximoY() == world.length
			);
		}
		
		private boolean temParede() {
			return false;
		}
		
		private boolean proximoMapeado() {
			return world[proximoY()][proximoX()] == 1;
		}
	}
	
	public static void main(String args[]) throws InterruptedException {
		//ultrasonicSensor = new UltrasonicSensor(SensorPort.S2);
//		colorSensor = new ColorSensor(SensorPort.S1);

		Robo robo = new Robo();

		//while (!Button.ENTER.isDown());
		
		for (int i = 0; i < (7 + 6 + 6 + 5); i++) {
			for (int j = 0; j < 3; j++) {
				if (!robo.detectaParedes()) {
					robo.posicaoAtualEhVertice();
				}

				if (robo.podeMovimentar()) {
					robo.movimenta();
					break;
				}
				
				robo.rotacionaDireita();
			}
		}
		
		for (int y = 0; y < world.length; y++) {
			for (int x = 0; x < world.length; x++) {
				System.out.print(world[y][x] + " ");
			}
			
			System.out.println();
		}
	}
}