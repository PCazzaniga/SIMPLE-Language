# S.I.M.P.L.E. dialects

S.I.M.P.L.E. programs can be written in 5 different variations of the grammar, called dialects.  

Four of them are based on real world human languages (English, French, Italian and Spanish) and the last one 
(Alternative) resembles the syntax of traditional programming languages.

The main dialect S.I.M.P.L.E. was designed around is the English one, so that is the one used in the [manual](manual.md).
This dictionary can be used as complementary reference for programming with the other dialects instead.

Note, any syntax element that is not specified for a certain dialect implies it is the same as in English.

---

|                     Syntax element                      |               French               |                Italian                |                 Spanish                 |     Alternative      |
|:-------------------------------------------------------:|:----------------------------------:|:-------------------------------------:|:---------------------------------------:|:--------------------:|
|           [Assignment](manual.md#Assignment)            |  `Fixe `_X_` à la valeur de `_Y_   |   `Imposta `_X_` al valore di `_Y_    |    `Establece `_X_` al valor de `_Y_    |  `let `_X_` = `_Y_   |
|              [Input](manual.md#Assignment)              |              `Entrée`              |                                       |                `Entrada`                |        `scan`        |
|             [Output](manual.md#Assignment)              |              `Sortie`              |                                       |                `Salida`                 |       `print`        |
|         [Comment delimiters](manual.md#Comment)         |                                    |                                       |                                         |  `//`_comment_`//`   |
|       [Equality](manual.md#Comparison-operation)        |                                    |                                       |                                         |         `==`         |
|          [If](manual.md#Conditional-statement)          |         `Si `_X_` alors:`          |          `Se `_X_` allora:`           |          `Si `_X_` entonces:`           |   `if `_X_` then:`   |
|       [Else if](manual.md#Conditional-statement)        |      `Sinon si `_X_` alors:`       |     `Altrimenti se `_X_` allora:`     |   `De lo contrario si`_X_` entonces:`   |  `elif `_X_` then:`  |
|         [Else](manual.md#Conditional-statement)         |              `Sinon:`              |             `Altrimenti:`             |           `De lo contrario:`            |       `else:`        |
|             [Call](manual.md#Function-call)             | `Exécute procédure `_X_` avec `_Y_ |   `Esegui procedura `_X_` con `_Y_    |  `Ejecuta procedimiento `_X_` con `_Y_  | `call `_X_` <- `_Y_  |
| [Function declaration](manual.md#Function-declaration)  | `Définis une procédure nommée `_X_ | `Definisci una procedura di nome `_X_ | `Define un procedimiento de nombre `_X_ |      `func `_X_      |
|  [Function details A](manual.md#Function-declaration)   |       ` qui produit un `_X_        |         ` che produce un `_X_         |          ` que produce un `_X_          |       ` : `_X_       |
|  [Function details B](manual.md#Function-declaration)   |       ` qui utilise un `_X_        |           ` che usa un `_X_           |            ` que usa un `_X_            |      ` ::: `_X_      |
|  [Function details C](manual.md#Function-declaration)   |        ` en utilisant `_X_         |             ` usando `_X_             |              ` usando `_X_              |      ` :: `_X_       |
|           [Scope delimiters](manual.md#Scope)           |                                    |                                       |                                         |    `{`_scope_`}`     |
|       [List insertion](manual.md#List-operation)        |      `Insère `_X_` dans `_Y_       |       `Inserisci `_X_` in `_Y_        |         `Inserta `_X_` en `_Y_          |  `add `_X_` in `_Y_  |
|        [List removal](manual.md#List-operation)         |         `Supprime de `_X_          |           `Rimuovi da `_X_            |             `Quita de `_X_              |     `remove `_X_     |
|        [Text to List](manual.md#List-operation)         |      `Sépare `_X_` dans `_Y_       |         `Separa `_X_` in `_Y_         |          `Separa `_X_` en `_Y_          | `split `_X_` in `_Y_ |
|        [List to Text](manual.md#List-operation)         |       `Unis `_X_` dans `_Y_        |         `Unisci `_X_` in `_Y_         |           `Une `_X_` en `_Y_            | `merge `_X_` in `_Y_ |
|                [True](manual.md#Literal)                |               `Vrai`               |                `Vero`                 |               `Verdadero`               |        `true`        |
|               [False](manual.md#Literal)                |               `Faux`               |                `Falso`                |                 `Falso`                 |       `false`        |
|              [Nothing](manual.md#Literal)               |               `Rien`               |               `Niente`                |                 `Nada`                  |        `null`        |
|           [Kit delimiters](manual.md#Literal)           |                                    |                                       |                                         |  `°{`_content_`}°`   |
|     [Logical operands](manual.md#Logical-operation)     |         `et`, `ou`, `non`          |            `e`, `o`, `non`            |             `y`, `o`, `no`              |  `&&`, `\|\|`, `!`   |
|           [Loop A](manual.md#Loop-statement)            |      `Répète pendant que `_X_      |          `Ripeti mentre `_X_          |          `Repite mientras `_X_          |   `loop while `_X_   |
|           [Loop B](manual.md#Loop-statement)            |        `Répète `_X_` fois`         |         `Ripeti `_X_` volte`          |          `Repite `_X_` veces`           |  `loop `_X_` times`  |
|               [Return](manual.md#Return)                |            `Rends `_X_             |             `Ritorna `_X_             |             `Devuelve `_X_              |     `return `_X_     |
|           [Counter](manual.md#Special-value)            |             `Compteur`             |              `Contatore`              |               `Contador`                |       `count`        |
|           [Size of](manual.md#Special-value)            |         `dimension de `_X_         |          `dimensione di `_X_          |           `dimensión de `_X_            |     `sizeof `_X_     |
|            [Random](manual.md#Special-value)            |            `Aléatoire`             |               `Casuale`               |                `Causal`                 |        `rand`        |
|     [Structure access](manual.md#Structure-access)      |      _X_` à la position `_Y_       |       _X_` alla posizione `_Y_        |        _X_` en la positión `_Y_         |     _X_` @ `_Y_      |
|     [Kit field access](manual.md#Structure-access)      |                                    |                                       |                                         |        `^`_X_        |
|              [Number type](manual.md#Type)              |              `Nombre`              |               `Numero`                |                `Número`                 |       `float`        |
|               [Text type](manual.md#Type)               |              `Texte`               |                `Testo`                |                 `Texto`                 |       `string`       |
|             [Boolean type](manual.md#Type)              |             `Booléen`              |              `Booleano`               |               `Booleano`                |        `bool`        |
|             [Sequence type](manual.md#Type)             |       `Séquence de `_X_ _Y_        |         `Sequenza di `_X_ _Y_         |         `Secuencia de `_X_ _Y_          |   `array `_X_ _Y_    |
|               [List type](manual.md#Type)               |           `Liste de `_X_           |            `Lista di `_X_             |             `Lista de `_X_              |      `list `_X_      |
|               [Kit type](manual.md#Type)                |          `Trousse de `_X_          |             `Kit di `_X_              |             `Equipo de `_X_             |     `struct `_X_     |
|      [Declaration](manual.md#Variable-declaration)      |    `Prépare un `_X_` nommé `_Y_    |    `Prepara un `_X_` di nome `_Y_     |    `Prepara un `_X_` de nombre `_Y_     |  `def `_X_` $ `_Y_   |
| [Declaration details A](manual.md#Variable-declaration) |          ` de valuer `_X_          |         ` con valore di `_X_          |           ` con valor de `_X_           |      ` := `_X_       |
|   [Declaration details B](manual.md#Type-declaration)   |      ` comme un nouveau type`      |         ` come un nuovo tipo`         |          ` como un nuevo tipo`          |      ` typedef`      |
