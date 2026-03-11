// Apply saved theme immediately to prevent flash of wrong theme
(function () {
    const saved = localStorage.getItem('lp-theme') || 'light';
    document.documentElement.setAttribute('data-theme', saved);
})();

function toggleTheme() {
    const current = document.documentElement.getAttribute('data-theme') || 'light';
    const next = current === 'dark' ? 'light' : 'dark';
    document.documentElement.setAttribute('data-theme', next);
    localStorage.setItem('lp-theme', next);
}
