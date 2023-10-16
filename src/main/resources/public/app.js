/*
 * Copyright 2023 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

document.addEventListener("DOMContentLoaded", function () {
    const charSel = document.getElementById('characterSel');
    const settSel = document.getElementById('settingSel');
    const plotSel = document.getElementById('plotSel');
    const languageSel = document.getElementById('languageSel');

    const charArea = document.getElementById('characterArea');
    const settArea = document.getElementById('settingArea');
    const plotArea = document.getElementById('plotArea');
    const storyArea = document.getElementById('storyArea');
    const languageArea = document.getElementById('languageArea');

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

    languageSel.addEventListener('sl-change', (e) => {
        languageArea.value = languageSel.getAllOptions()[languageSel.value].getTextLabel();
    });

    generateBtn.addEventListener('click', async(e) => {
        generateBtn.loading = true;
        generateBtn.disabled = true;
        const resp = await fetch('/story/generate?' + new URLSearchParams({
            character: charArea.value,
            setting: settArea.value,
            plot: plotArea.value,
            language: languageArea.value
        }));
        const json = await resp.json();
        storyArea.value = json.join('\n\n');
        generateBtn.loading = false;
        generateBtn.disabled = false;
    });
});

