airpotrs-search
===============

Консольное приложение для поиска данных об аэропортах

Запуск приложения
=================

- ``java -Xmx7m -jar airpotrs-search-1.0-SNAPSHOT.jar``


Структура проекта
=================

Проект содержит 4 основных пакета: ``CSVReader``, ``Extension``, ``FileReader``, ``FilterParser``

CSVReader
---------
Содержит 3 класса:
1. ``CSVReader`` - класс, содержащий в себе описательную структуру CSV файла.
2. ``CSVRows`` - класс, представляет коллекцию элементов ``CSVRow``.
3. ``CSVRow`` - класс, описывает строку CSV файла.

FileReader
----------

Содержит 2 элемента:
1. ``LineRead`` - интерфейс, который описывает метод обратного вызова.
2. ``FileRead`` - класс, который позволяет, считать весь или построчно файл.

FilterParser
------------

Распаршивание фильтра было сделано с помощью ``Дерево синтаксического разбора``.
Содержит 2 класса:
1. ``Node`` - класс, который описывает конкретную часть выражения
2. ``FilterParser`` - класс, который производит разбор фильра и выполняет фильтрацию.

Формирование фильтра
====================

Фильтр - ``column[1]<>2 & (column[5]=’GKA’ & column[7]>-6.09)``.
Важно, чтобы между отношениями был пробел.

Пример
======

Поиск по ``Bo``:

![alt text](https://sun9-62.userapi.com/impg/2eLOR21_eKc5fgaisyEucPsRRiGFgmWocxmrtQ/0MeARXo8-U0.jpg?size=700x81&quality=96&sign=2f5c1d244f3e90815d9e5f106d535c2e&type=album)

Поиск по ``Bower``:

![alt text](https://sun1-94.userapi.com/impg/RgavUn5h5ErwyvORMrRpCO62X4ScAX8U0wsHMA/jxTQ_D5Rae4.jpg?size=637x143&quality=96&sign=138665f98c9781f1239f13b91b372ecf&type=album)

Поиск по фильтру ``column[1]<>2 & (column[5]=’GKA’ & column[7]>-6.09)`` и ``Go``

![alt text](https://sun9-72.userapi.com/impg/Fhc-RMDt311rnuMrKbotR3zDusi8kfjTMkbgzw/uFO0C5aR6Vo.jpg?size=809x138&quality=96&sign=e00ead3f6f2eab2f222f5f3046439510&type=album)
