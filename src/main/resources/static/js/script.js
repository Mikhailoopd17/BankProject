window.onload = function() {
    var divs = document.querySelectorAll('.op + div'),
    inp = document.querySelectorAll('[name="transfer"]'),
    fn = function(input) {
        input.onclick = function() {
            Array.prototype.forEach.call(divs, function(div, i) {
                div.style.display = inp[i].checked ? "block" : "none"
            })
        }
    };
    Array.prototype.forEach.call(inp, fn);
}
