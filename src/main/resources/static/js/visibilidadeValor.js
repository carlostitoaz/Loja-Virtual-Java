let visivel = true;

function visibilidadeValor() {
    const elementos = document.querySelectorAll('.card-valor');
    const icone = document.getElementById('icone-visibilidade');

    elementos.forEach(elemento => {
        if (visivel) {
            elemento.dataset.valorOriginal = elemento.textContent;
            elemento.textContent = "***";
        } else {
            elemento.textContent = elemento.dataset.valorOriginal || elemento.textContent;
        }
    });

    if (icone) {
        icone.classList.toggle('bi-eye');
        icone.classList.toggle('bi-eye-slash');
    }

    visivel = !visivel;
}
