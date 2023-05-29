document.addEventListener("DOMContentLoaded", function () {
    const charSel = document.getElementById('characterSel');
    const settSel = document.getElementById('settingSel');
    const plotSel = document.getElementById('plotSel');

    const charArea = document.getElementById('characterArea');
    const settArea = document.getElementById('settingArea');
    const plotArea = document.getElementById('plotArea');
    const storyArea = document.getElementById('storyArea');

    const generateBtn = document.getElementById('generateBtn');

    charSel.addEventListener('sl-change', (e) => {
        charArea.value = charSel.getAllOptions()[charSel.value].getTextLabel();
    });

    settSel.addEventListener('sl-change', (e) => {
        settArea.value = settSel.getAllOptions()[settSel.value].getTextLabel();
    });

    plotSel.addEventListener('sl-change', (e) => {
        plotArea.value = plotSel.getAllOptions()[plotSel.value].getTextLabel();
    });

    generateBtn.addEventListener('click', async(e) => {
        generateBtn.loading = true;
        generateBtn.disabled = true;
        const resp = await fetch('/story/generate?' + new URLSearchParams({
            character: charArea.value,
            setting: settArea.value,
            plot: plotArea.value
        }));
        const json = await resp.json();
        storyArea.value = json.join('\n\n');
        generateBtn.loading = false;
        generateBtn.disabled = false;
    });
});

