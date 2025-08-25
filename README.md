RefrigerantToolsApp/
│
├── app/
│   ├── build.gradle.kts          # Gradle config for app
│   ├── src/main/
│   │   ├── AndroidManifest.xml   # Декларація Activity + дозволи (інтернет, доступ до даних)
│   │   │
│   │   ├── java/com/example/refrigeranttools/
│   │   │   ├── MainActivity.kt           # Головний екран (навігація по вкладках)
│   │   │   ├── ui/
│   │   │   │   ├── HomeFragment.kt       # Домашня сторінка з меню
│   │   │   │   ├── AiAssistantFragment.kt# AI помічник для вирішення помилок
│   │   │   │   ├── DatabaseFragment.kt   # Онлайн база кондиціонерів
│   │   │   │   ├── CalculatorFragment.kt # Калькулятор дозаправки газу
│   │   │   │   ├── SpecsFragment.kt      # Технічні характеристики кондиціонера
│   │   │   │   ├── ComparisonFragment.kt # Порівняння тиску/температури
│   │   │   │   ├── HistoryFragment.kt    # Історія запитів
│   │   │   │   └── SettingsFragment.kt   # Налаштування (мова, офлайн/онлайн режим)
│   │   │   │
│   │   │   ├── data/
│   │   │   │   ├── ApiService.kt         # API для отримання онлайн бази кондиціонерів
│   │   │   │   ├── DatabaseHelper.kt     # SQLite/Room для офлайн даних
│   │   │   │   └── Models.kt             # Моделі даних (кондиціонери, гази, характеристики)
│   │   │   │
│   │   │   ├── utils/
│   │   │   │   ├── CalculatorUtils.kt    # Формули для розрахунку газу
│   │   │   │   ├── ValidationUtils.kt    # Перевірка введених даних
│   │   │   │   └── Constants.kt          # Константи (R32, R410A, R134a тощо)
│   │   │   │
│   │   │   └── ai/
│   │   │       ├── AiEngine.kt           # Логіка AI помічника
│   │   │       └── PromptBuilder.kt      # Генерація промтів для AI
│   │   │
│   │   ├── res/
│   │   │   ├── layout/                   # XML UI-файли
│   │   │   │   ├── fragment_home.xml
│   │   │   │   ├── fragment_ai_assistant.xml
│   │   │   │   ├── fragment_database.xml
│   │   │   │   ├── fragment_calculator.xml
│   │   │   │   ├── fragment_specs.xml
│   │   │   │   ├── fragment_comparison.xml
│   │   │   │   ├── fragment_history.xml
│   │   │   │   └── fragment_settings.xml
│   │   │   │
│   │   │   ├── values/
│   │   │   │   ├── colors.xml
│   │   │   │   ├── strings.xml
│   │   │   │   └── themes.xml
│   │   │   │
│   │   │   └── drawable/                 # Іконки, ілюстрації
│   │   │
│   │   └── assets/                       # Локальна база даних, якщо є
│   │
│   └── proguard-rules.pro
│
└── build.gradle.kts (root)
└── settings.gradle.kts
