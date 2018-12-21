package vibe.com.br.vibe.model;

public class Job {
    private String id;
    private String area_de_atuacao;
    private String experiencia;
    private String habilidade_chave;
    private String nivel_de_escolaridade;
    private String posicao;
    private String tipo_de_contrato;
    private String titulo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArea_de_atuacao() {
        return area_de_atuacao;
    }

    public void setArea_de_atuacao(String area_de_atuacao) {
        this.area_de_atuacao = area_de_atuacao;
    }

    public String getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }

    public String getHabilidade_chave() {
        return habilidade_chave;
    }

    public void setHabilidade_chave(String habilidade_chave) {
        this.habilidade_chave = habilidade_chave;
    }

    public String getNivel_de_escolaridade() {
        return nivel_de_escolaridade;
    }

    public void setNivel_de_escolaridade(String nivel_de_escolaridade) {
        this.nivel_de_escolaridade = nivel_de_escolaridade;
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }

    public String getTipo_de_contrato() {
        return tipo_de_contrato;
    }

    public void setTipo_de_contrato(String tipo_de_contrato) {
        this.tipo_de_contrato = tipo_de_contrato;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
