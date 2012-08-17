package br.com.caelum.agiletickets.models;

public class SessaoBuilder {

	class CriaSessao {

		int num = 0;

		public CriaSessao(int num) {
			this.num = num;
		}

		public Sessao ingressos() {
			Sessao sessao = new Sessao();
			sessao.setTotalIngressos(num);
			return sessao;
		}

	}

	public Sessao getSessao(int num) {
		return new CriaSessao(num).ingressos();
	}

}
