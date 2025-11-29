export function drawRomanClock(container, hours, mins, secs = null) {
    const div = container;

    let canvas = div.querySelector("canvas");
    if (!canvas) {
        canvas = document.createElement("canvas");
        canvas.width = 300;
        canvas.height = 300;
        div.appendChild(canvas);
    }

    const ctx = canvas.getContext("2d");
    const w = canvas.width;
    const h = canvas.height;
    const r = Math.min(w, h) / 2;

    ctx.clearRect(0, 0, w, h);
    ctx.save();
    ctx.translate(w / 2, h / 2);

    const roman = ["XII","I","II","III","IV","V","VI","VII","VIII","IX","X","XI"];

    // Outer circle
    ctx.beginPath();
    ctx.arc(0, 0, r - 5, 0, Math.PI * 2);
    ctx.lineWidth = 4;
    ctx.stroke();

    // Roman numerals
    ctx.font = `${r * 0.18}px serif`;
    ctx.textAlign = "center";
    ctx.textBaseline = "middle";
    roman.forEach((num, i) => {
        const angle = (i * Math.PI * 2) / 12 - Math.PI / 2;
        ctx.fillText(num, Math.cos(angle) * (r * 0.75), Math.sin(angle) * (r * 0.75));
    });

    // Angles
    const minuteAngle = (mins / 60) * Math.PI * 2;
    const hourAngle = ((hours % 12) / 12) * Math.PI * 2 + minuteAngle / 12;

    // Hour hand
    ctx.save();
    ctx.rotate(hourAngle - Math.PI / 2);
    ctx.lineWidth = 6;
    ctx.beginPath();
    ctx.moveTo(0, 0);
    ctx.lineTo(r * 0.5, 0);
    ctx.stroke();
    ctx.restore();

    // Minute hand
    ctx.save();
    ctx.rotate(minuteAngle - Math.PI / 2);
    ctx.lineWidth = 4;
    ctx.beginPath();
    ctx.moveTo(0, 0);
    ctx.lineTo(r * 0.75, 0);
    ctx.stroke();
    ctx.restore();

    // 🟥 Second hand (optional)
    if (secs !== null && !isNaN(secs)) {
        const secondAngle = (secs / 60) * Math.PI * 2;

        ctx.save();
        ctx.rotate(secondAngle - Math.PI / 2);
        ctx.strokeStyle = "red";
        ctx.lineWidth = 2;

        ctx.beginPath();
        ctx.moveTo(0, 0);
        ctx.lineTo(r * 0.85, 0);
        ctx.stroke();

        ctx.restore();

        // restore stroke style for other drawings
        ctx.strokeStyle = "black";
    }

    // Center dot
    ctx.beginPath();
    ctx.arc(0, 0, 5, 0, Math.PI * 2);
    ctx.fill();

    ctx.restore();
}


export function computeBisectingSecond(hours, mins) {
    const hourAngle   = normalize((hours % 12) * 30 + mins * 0.5);
    const minuteAngle = normalize(mins * 6);

    // compute midpoint angle
    let diff = normalize(minuteAngle - hourAngle);

    // smaller angle direction
    if (diff > 180) diff -= 360;

    // midpoint angle
    const bisectAngle = normalize(hourAngle + diff / 2);

    // seconds = angle / 6
    return Math.round(bisectAngle / 6);
}

export function normalize(deg) {
    return (deg % 360 + 360) % 360;
}
