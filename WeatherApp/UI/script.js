// Weather Dashboard JavaScript

class WeatherDashboard {
    constructor() {
        this.baseApiUrl = 'http://localhost:8080/weather/forecast';
        this.currentTab = 'temperature';
        this.weatherData = null;
        this.chart = null;
        this.currentCity = 'chennai';
        this.currentDays = 2;
        
        this.init();
    }
    
    init() {
        this.setupEventListeners();
        this.loadWeatherData();
        this.updateCurrentTime();
        
        // Update time every minute
        setInterval(() => this.updateCurrentTime(), 60000);
    }
    
    setupEventListeners() {
        // Tab switching
        document.querySelectorAll('.tab-button').forEach(button => {
            button.addEventListener('click', (e) => {
                this.switchTab(e.target.dataset.tab);
            });
        });
        
        // Search button
        document.getElementById('search-btn').addEventListener('click', () => {
            this.handleSearch();
        });
        
        // Enter key on city input
        document.getElementById('city-input').addEventListener('keypress', (e) => {
            if (e.key === 'Enter') {
                this.handleSearch();
            }
        });
        
        // Days selection change
        document.getElementById('days-input').addEventListener('change', () => {
            this.handleSearch();
        });
    }
    
    handleSearch() {
        const cityInput = document.getElementById('city-input');
        const daysInput = document.getElementById('days-input');
        const searchBtn = document.getElementById('search-btn');
        
        const city = cityInput.value.trim();
        const days = parseInt(daysInput.value);
        
        if (!city) {
            this.showInputError('Please enter a city name');
            cityInput.focus();
            return;
        }
        
        // Update current values
        this.currentCity = city;
        this.currentDays = days;
        
        // Disable button and show loading state
        searchBtn.disabled = true;
        searchBtn.textContent = 'Loading...';
        
        // Load weather data
        this.loadWeatherData().finally(() => {
            searchBtn.disabled = false;
            searchBtn.textContent = 'Get Weather';
        });
    }
    
    buildApiUrl() {
        return `${this.baseApiUrl}?city=${encodeURIComponent(this.currentCity)}&days=${this.currentDays}`;
    }
    
    async loadWeatherData() {
        const apiUrl = this.buildApiUrl();
        
        try {
            console.log('Fetching weather data from:', apiUrl);
            const response = await fetch(apiUrl);
            
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            
            this.weatherData = await response.json();
            console.log('Weather data received:', this.weatherData);
            
            this.displayWeatherData();
            this.hideError();
            this.clearInputError();
            
        } catch (error) {
            console.error('Error fetching weather data:', error);
            this.showError(`Unable to load weather data for ${this.currentCity}. Please check if the city name is correct and your API is running.`);
        }
    }
    
    showInputError(message) {
        // Remove existing error if any
        this.clearInputError();
        
        const cityInput = document.getElementById('city-input');
        const errorDiv = document.createElement('div');
        errorDiv.className = 'input-error';
        errorDiv.textContent = message;
        errorDiv.style.cssText = `
            color: #e74c3c;
            font-size: 0.85rem;
            margin-top: 5px;
        `;
        
        cityInput.parentNode.appendChild(errorDiv);
        cityInput.style.borderColor = '#e74c3c';
        
        setTimeout(() => this.clearInputError(), 3000);
    }
    
    clearInputError() {
        const errorDiv = document.querySelector('.input-error');
        const cityInput = document.getElementById('city-input');
        
        if (errorDiv) {
            errorDiv.remove();
        }
        
        cityInput.style.borderColor = '#e9ecef';
    }
    
    displayWeatherData() {
        const { weatherResponse, dayTemp } = this.weatherData;
        
        // Update main weather display
        document.getElementById('main-temperature').textContent = 
            `${Math.round(weatherResponse.temperature)}°`;
        document.getElementById('current-condition').textContent = weatherResponse.condition;
        
        // Update weather details (using mock data for now)
        document.getElementById('precipitation').textContent = '5%';
        document.getElementById('humidity').textContent = '83%';
        document.getElementById('wind').textContent = '11 km/h';
        
        // Update weather icon
        this.updateWeatherIcon(weatherResponse.condition);
        
        // Update forecast
        this.displayForecast(dayTemp);
        
        // Update chart
        this.updateChart();
    }
    
    updateWeatherIcon(condition) {
        const iconContainer = document.getElementById('main-weather-icon');
        const iconId = this.getWeatherIconId(condition);
        
        iconContainer.innerHTML = `
            <svg class="weather-icon" viewBox="0 0 80 60">
                <use href="#${iconId}"></use>
            </svg>
        `;
    }
    
    getWeatherIconId(condition) {
        const conditionLower = condition.toLowerCase();
        
        if (conditionLower.includes('sun') || conditionLower.includes('clear')) {
            return 'sunny';
        } else if (conditionLower.includes('rain') || conditionLower.includes('drizzle')) {
            return 'rainy';
        } else if (conditionLower.includes('mist') || conditionLower.includes('fog')) {
            return 'misty';
        } else if (conditionLower.includes('cloud')) {
            return 'cloudy';
        } else if (conditionLower.includes('partly')) {
            return 'partly-cloudy';
        }
        
        return 'cloudy'; // default
    }
    
    displayForecast(dayTemp) {
        const forecastGrid = document.getElementById('forecast-grid');
        const days = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
        
        forecastGrid.innerHTML = '';
        
        // Only show the exact number of days returned by API
        dayTemp.forEach((dayData, i) => {
            const date = new Date(dayData.date);
            
            const forecastItem = document.createElement('div');
            forecastItem.className = 'forecast-item';
            
            // Determine weather condition based on temperature
            let condition = 'cloudy';
            if (dayData.avgTemp > 32) condition = 'sunny';
            else if (dayData.avgTemp < 25) condition = 'rainy';
            else if (dayData.avgTemp > 28 && dayData.maxTemp > 33) condition = 'partly-cloudy';
            
            const iconId = this.getWeatherIconId(condition);
            
            // Format day name
            const dayName = i === 0 ? 'Today' : days[date.getDay()];
            
            forecastItem.innerHTML = `
                <div class="forecast-day">${dayName}</div>
                <svg class="forecast-icon" viewBox="0 0 80 60">
                    <use href="#${iconId}"></use>
                </svg>
                <div class="forecast-temps">
                    <div class="forecast-high">${Math.round(dayData.maxTemp)}°</div>
                    <div class="forecast-low">${Math.round(dayData.minTemp)}°</div>
                </div>
            `;
            
            forecastGrid.appendChild(forecastItem);
        });
    }
    
    updateCurrentTime() {
        const now = new Date();
        const options = { 
            weekday: 'long', 
            hour: 'numeric', 
            minute: '2-digit',
            hour12: true 
        };
        const timeString = now.toLocaleDateString('en-US', options);
        document.getElementById('current-time').textContent = timeString;
    }
    
    switchTab(tabName) {
        // Update active tab
        document.querySelectorAll('.tab-button').forEach(button => {
            button.classList.remove('active');
        });
        document.querySelector(`[data-tab="${tabName}"]`).classList.add('active');
        
        this.currentTab = tabName;
        this.updateChart();
    }
    
    updateChart() {
        if (!this.weatherData) return;
        
        const canvas = document.getElementById('weather-chart');
        const ctx = canvas.getContext('2d');
        
        // Set canvas size
        canvas.width = canvas.offsetWidth;
        canvas.height = canvas.offsetHeight;
        
        // Clear canvas
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        
        this.drawTemperatureChart(ctx, canvas.width, canvas.height);
    }
    
    drawTemperatureChart(ctx, width, height) {
        const { dayTemp } = this.weatherData;
        
        // Generate hourly data points for smooth curve
        const hours = ['12 am', '3 am', '6 am', '9 am', '12 pm', '3 pm', '6 pm', '9 pm'];
        const temps = [28, 28, 28, 31, 33, 35, 33, 30]; // Sample hourly temperatures
        
        const padding = 40;
        const chartWidth = width - padding * 2;
        const chartHeight = height - padding * 2;
        
        // Calculate positions
        const stepX = chartWidth / (temps.length - 1);
        const minTemp = Math.min(...temps) - 2;
        const maxTemp = Math.max(...temps) + 2;
        const tempRange = maxTemp - minTemp;
        
        // Draw area fill
        ctx.beginPath();
        ctx.moveTo(padding, height - padding);
        
        temps.forEach((temp, i) => {
            const x = padding + i * stepX;
            const y = padding + (maxTemp - temp) * (chartHeight / tempRange);
            
            if (i === 0) {
                ctx.lineTo(x, y);
            } else {
                ctx.lineTo(x, y);
            }
        });
        
        ctx.lineTo(width - padding, height - padding);
        ctx.closePath();
        ctx.fillStyle = 'rgba(243, 156, 18, 0.1)';
        ctx.fill();
        
        // Draw line
        ctx.beginPath();
        temps.forEach((temp, i) => {
            const x = padding + i * stepX;
            const y = padding + (maxTemp - temp) * (chartHeight / tempRange);
            
            if (i === 0) {
                ctx.moveTo(x, y);
            } else {
                ctx.lineTo(x, y);
            }
        });
        ctx.strokeStyle = '#f39c12';
        ctx.lineWidth = 3;
        ctx.lineCap = 'round';
        ctx.lineJoin = 'round';
        ctx.stroke();
        
        // Draw points and labels
        ctx.fillStyle = '#f39c12';
        ctx.strokeStyle = 'white';
        ctx.lineWidth = 2;
        
        temps.forEach((temp, i) => {
            const x = padding + i * stepX;
            const y = padding + (maxTemp - temp) * (chartHeight / tempRange);
            
            // Draw point
            ctx.beginPath();
            ctx.arc(x, y, 4, 0, Math.PI * 2);
            ctx.fill();
            ctx.stroke();
            
            // Draw temperature label
            ctx.fillStyle = '#2c3e50';
            ctx.font = '12px -apple-system, BlinkMacSystemFont, sans-serif';
            ctx.textAlign = 'center';
            ctx.fillText(`${temp}`, x, y - 12);
            
            // Draw time label
            ctx.fillStyle = '#7f8c8d';
            ctx.font = '11px -apple-system, BlinkMacSystemFont, sans-serif';
            ctx.fillText(hours[i], x, height - 5);
            
            ctx.fillStyle = '#f39c12';
        });
    }
    
    showError(message = 'Unable to load weather data. Please make sure your API is running on http://localhost:8080') {
        const errorDiv = document.getElementById('error') || this.createErrorDiv();
        errorDiv.querySelector('p').textContent = message;
        errorDiv.style.display = 'block';
        
        const weatherContent = document.querySelector('.main-weather-card');
        if (weatherContent) {
            weatherContent.style.display = 'none';
        }
        
        const forecastSection = document.querySelector('.forecast-section');
        if (forecastSection) {
            forecastSection.style.display = 'none';
        }
    }
    
    hideError() {
        const errorDiv = document.getElementById('error');
        if (errorDiv) {
            errorDiv.style.display = 'none';
        }
        
        const weatherContent = document.querySelector('.main-weather-card');
        if (weatherContent) {
            weatherContent.style.display = 'block';
        }
        
        const forecastSection = document.querySelector('.forecast-section');
        if (forecastSection) {
            forecastSection.style.display = 'block';
        }
    }
    
    createErrorDiv() {
        const errorDiv = document.createElement('div');
        errorDiv.id = 'error';
        errorDiv.className = 'error';
        errorDiv.innerHTML = `
            <h3>Weather data unavailable</h3>
            <p></p>
            <button class="refresh-btn" onclick="weatherDashboard.handleSearch()">Try Again</button>
        `;
        
        document.querySelector('.container').insertBefore(
            errorDiv, 
            document.querySelector('.main-weather-card')
        );
        
        return errorDiv;
    }
}

// Initialize the dashboard when page loads
let weatherDashboard;

document.addEventListener('DOMContentLoaded', () => {
    weatherDashboard = new WeatherDashboard();
    
    // Handle window resize for chart
    window.addEventListener('resize', () => {
        if (weatherDashboard.weatherData) {
            setTimeout(() => weatherDashboard.updateChart(), 100);
        }
    });
});