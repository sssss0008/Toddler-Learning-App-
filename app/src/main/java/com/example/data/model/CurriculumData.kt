package com.example.data.model

import androidx.compose.ui.graphics.Color
import com.example.ui.theme.*

object CurriculumData {

    val abcLessons = listOf(
        LessonItem("abc_a", ModuleType.ABC, "A", "Apple", "🍎", "A is for Apple! Crisp, red, and sweet to eat!", pronunciation = "/eɪ/", category = "Fruit", accentColor = Color(0xFFEF5350)),
        LessonItem("abc_b", ModuleType.ABC, "B", "Butterfly", "🦋", "B is for Butterfly! Fluttering its colorful wings!", pronunciation = "/biː/", category = "Insects", accentColor = Color(0xFF42A5F5)),
        LessonItem("abc_c", ModuleType.ABC, "C", "Cat", "🐱", "C is for Cat! Meow meow, soft and cuddly!", soundPrompt = "Meow!", pronunciation = "/siː/", category = "Animals", accentColor = Color(0xFFFFA726)),
        LessonItem("abc_d", ModuleType.ABC, "D", "Dog", "🐶", "D is for Dog! Woof woof, friendly puppy!", soundPrompt = "Woof woof!", pronunciation = "/diː/", category = "Animals", accentColor = Color(0xFF8D6E63)),
        LessonItem("abc_e", ModuleType.ABC, "E", "Elephant", "🐘", "E is for Elephant! Giant ears and a long trunk!", soundPrompt = "Pawoo!", pronunciation = "/iː/", category = "Wild", accentColor = Color(0xFF78909C)),
        LessonItem("abc_f", ModuleType.ABC, "F", "Fish", "🐟", "F is for Fish! Swimming and splashing in water!", soundPrompt = "Glub glub!", pronunciation = "/ɛf/", category = "Water", accentColor = Color(0xFF26C6DA)),
        LessonItem("abc_g", ModuleType.ABC, "G", "Giraffe", "🦒", "G is for Giraffe! Tallest friend with a very long neck!", pronunciation = "/dʒiː/", category = "Wild", accentColor = Color(0xFFFFB300)),
        LessonItem("abc_h", ModuleType.ABC, "H", "Horse", "🐴", "H is for Horse! Galloping fast across green fields!", soundPrompt = "Neigh!", pronunciation = "/eɪtʃ/", category = "Farm", accentColor = Color(0xFFA1887F)),
        LessonItem("abc_i", ModuleType.ABC, "I", "Ice Cream", "🍦", "I is for Ice Cream! Cold, yummy, and sweet!", pronunciation = "/aɪ/", category = "Treat", accentColor = Color(0xFFEC407A)),
        LessonItem("abc_j", ModuleType.ABC, "J", "Juice", "🧃", "J is for Juice! Refreshing orange juice in a box!", pronunciation = "/dʒeɪ/", category = "Drink", accentColor = Color(0xFFFF7043)),
        LessonItem("abc_k", ModuleType.ABC, "K", "Kangaroo", "🦘", "K is for Kangaroo! Hopping high with a baby in the pouch!", soundPrompt = "Boing boing!", pronunciation = "/keɪ/", category = "Animals", accentColor = Color(0xFFFF8A65)),
        LessonItem("abc_l", ModuleType.ABC, "L", "Lion", "🦁", "L is for Lion! Roar! King of the savanna!", soundPrompt = "Roar!", pronunciation = "/ɛl/", category = "Wild", accentColor = Color(0xFFFFCA28)),
        LessonItem("abc_m", ModuleType.ABC, "M", "Monkey", "🐵", "M is for Monkey! Swinging from tree branches!", soundPrompt = "Ooh ooh aah aah!", pronunciation = "/ɛm/", category = "Animals", accentColor = Color(0xFF8D6E63)),
        LessonItem("abc_n", ModuleType.ABC, "N", "Nest", "🪺", "N is for Nest! Cozy home for little baby birds!", pronunciation = "/ɛn/", category = "Nature", accentColor = Color(0xFF8D6E63)),
        LessonItem("abc_o", ModuleType.ABC, "O", "Owl", "🦉", "O is for Owl! Wise nocturnal bird that says hoot hoot!", soundPrompt = "Hoot hoot!", pronunciation = "/oʊ/", category = "Birds", accentColor = Color(0xFF7E57C2)),
        LessonItem("abc_p", ModuleType.ABC, "P", "Penguin", "🐧", "P is for Penguin! Waddling happily on icy snow!", soundPrompt = "Honk honk!", pronunciation = "/piː/", category = "Birds", accentColor = Color(0xFF263238)),
        LessonItem("abc_q", ModuleType.ABC, "Q", "Queen", "👑", "Q is for Queen! Wearing a shiny golden crown!", pronunciation = "/kjuː/", category = "Royalty", accentColor = Color(0xFFFFD54F)),
        LessonItem("abc_r", ModuleType.ABC, "R", "Rainbow", "🌈", "R is for Rainbow! Seven gorgeous colors across the sky!", pronunciation = "/ɑːr/", category = "Sky", accentColor = Color(0xFFAB47BC)),
        LessonItem("abc_s", ModuleType.ABC, "S", "Sun", "☀️", "S is for Sun! Warm, bright, and cheerful in the morning!", pronunciation = "/ɛs/", category = "Sky", accentColor = Color(0xFFFFEE58)),
        LessonItem("abc_t", ModuleType.ABC, "T", "Train", "🚂", "T is for Train! Choo choo chug chug down the tracks!", soundPrompt = "Choo choo!", pronunciation = "/tiː/", category = "Vehicle", accentColor = Color(0xFF42A5F5)),
        LessonItem("abc_u", ModuleType.ABC, "U", "Umbrella", "☂️", "U is for Umbrella! Keeping us dry in rainy weather!", pronunciation = "/juː/", category = "Objects", accentColor = Color(0xFF7E57C2)),
        LessonItem("abc_v", ModuleType.ABC, "V", "Violin", "🎻", "V is for Violin! Playing sweet musical melodies!", soundPrompt = "La la la!", pronunciation = "/viː/", category = "Music", accentColor = Color(0xFF8D6E63)),
        LessonItem("abc_w", ModuleType.ABC, "W", "Whale", "🐋", "W is for Whale! Huge gentle friend swimming in the ocean!", soundPrompt = "Whoosh splash!", pronunciation = "/ˈdʌbəl.juː/", category = "Ocean", accentColor = Color(0xFF29B6F6)),
        LessonItem("abc_x", ModuleType.ABC, "X", "Xylophone", "🎼", "X is for Xylophone! Ding dong, striking colorful bars!", soundPrompt = "Ding dong!", pronunciation = "/ˈzaɪ.lə.foʊn/", category = "Music", accentColor = Color(0xFF66BB6A)),
        LessonItem("abc_y", ModuleType.ABC, "Y", "Yacht", "⛵", "Y is for Yacht! Gliding smoothly on sparkling waves!", pronunciation = "/waɪ/", category = "Water", accentColor = Color(0xFF26A69A)),
        LessonItem("abc_z", ModuleType.ABC, "Z", "Zebra", "🦓", "Z is for Zebra! Black and white stripes running free!", soundPrompt = "Neigh!", pronunciation = "/zɛd/", category = "Wild", accentColor = Color(0xFF37474F))
    )

    val numberLessons = listOf(
        LessonItem("num_1", ModuleType.NUMBERS, "1", "One Sun", "☀️", "One! One shining golden sun in the sky.", pronunciation = "One", realWorldExample = "1 Nose on your face!"),
        LessonItem("num_2", ModuleType.NUMBERS, "2", "Two Shoes", "👟👟", "Two! A pair of comfy shoes for two little feet.", pronunciation = "Two", realWorldExample = "2 Eyes to see the world!"),
        LessonItem("num_3", ModuleType.NUMBERS, "3", "Three Apples", "🍎🍎🍎", "Three! Three sweet red apples on a tree.", pronunciation = "Three", realWorldExample = "3 Wheels on a tricycle!"),
        LessonItem("num_4", ModuleType.NUMBERS, "4", "Four Puppies", "🐶🐶🐶🐶", "Four! Four playful puppies wagging their tails.", pronunciation = "Four", realWorldExample = "4 Legs on a chair!"),
        LessonItem("num_5", ModuleType.NUMBERS, "5", "Five Stars", "⭐⭐⭐⭐⭐", "Five! High five! Five bright sparkling stars.", pronunciation = "Five", realWorldExample = "5 Fingers on your hand!"),
        LessonItem("num_6", ModuleType.NUMBERS, "6", "Six Flowers", "🌸🌸🌸🌸🌸🌸", "Six! Six blooming flowers in the sunny garden.", pronunciation = "Six"),
        LessonItem("num_7", ModuleType.NUMBERS, "7", "Seven Colors", "🌈", "Seven! Seven vibrant colors of the rainbow.", pronunciation = "Seven"),
        LessonItem("num_8", ModuleType.NUMBERS, "8", "Eight Tentacles", "🐙", "Eight! Eight wavy tentacles on an octopus!", pronunciation = "Eight"),
        LessonItem("num_9", ModuleType.NUMBERS, "9", "Nine Balloons", "🎈🎈🎈🎈🎈🎈🎈🎈🎈", "Nine! Nine colorful balloons flying in the air!", pronunciation = "Nine"),
        LessonItem("num_10", ModuleType.NUMBERS, "10", "Ten Fingers", "🙌", "Ten! Ten dancing fingers! Let's clap together!", pronunciation = "Ten", realWorldExample = "10 Toes on your feet!")
    )

    val colorLessons = listOf(
        LessonItem("col_red", ModuleType.COLORS, "Red", "Like a Strawberry", "🍓", "Red is warm and bright, like juicy strawberries and fire engines!", category = "Warm", accentColor = Color(0xFFE53935)),
        LessonItem("col_blue", ModuleType.COLORS, "Blue", "Like the Ocean", "🌊", "Blue is cool and calm, like the wide open sky and deep ocean!", category = "Cool", accentColor = Color(0xFF1E88E5)),
        LessonItem("col_yellow", ModuleType.COLORS, "Yellow", "Like the Sun", "☀️", "Yellow is happy and sunny, like yellow bananas and bright sunflowers!", category = "Warm", accentColor = Color(0xFFFDD835)),
        LessonItem("col_green", ModuleType.COLORS, "Green", "Like a Leaf", "🍃", "Green is fresh and lively, like tall trees, grass, and bouncy frogs!", category = "Cool", accentColor = Color(0xFF43A047)),
        LessonItem("col_orange", ModuleType.COLORS, "Orange", "Like an Orange", "🍊", "Orange is energetic, like sweet oranges and carrot snacks!", category = "Warm", accentColor = Color(0xFFFB8C00)),
        LessonItem("col_purple", ModuleType.COLORS, "Purple", "Like Grapes", "🍇", "Purple is royal and sweet, like tasty grapes and lavender flowers!", category = "Cool", accentColor = Color(0xFF8E24AA)),
        LessonItem("col_pink", ModuleType.COLORS, "Pink", "Like a Flamingo", "🦩", "Pink is cheerful and gentle, like flamingos and cotton candy!", category = "Warm", accentColor = Color(0xFFD81B60)),
        LessonItem("col_brown", ModuleType.COLORS, "Brown", "Like a Teddy Bear", "🧸", "Brown is cozy and earthy, like teddy bears, chocolate, and trees!", category = "Earth", accentColor = Color(0xFF6D4C41)),
        LessonItem("col_black", ModuleType.COLORS, "Black", "Like the Night Sky", "🌌", "Black is deep, like the night sky where stars twinkle bright!", category = "Neutral", accentColor = Color(0xFF212121)),
        LessonItem("col_white", ModuleType.COLORS, "White", "Like Fluffy Snow", "⛄", "White is soft and pure, like puffy clouds and chilly snowmen!", category = "Neutral", accentColor = Color(0xFF78909C))
    )

    val shapeLessons = listOf(
        LessonItem("shp_circle", ModuleType.SHAPES, "Circle", "Round and Smooth", "⭕", "A circle is round with no corners at all, just like a bouncing ball!", realWorldExample = "Ball, Clock, Coin, Orange", accentColor = Color(0xFFE53935)),
        LessonItem("shp_square", ModuleType.SHAPES, "Square", "Four Equal Sides", "⏹️", "A square has four straight sides that are all exactly the same!", realWorldExample = "Window, Picture frame, Building block", accentColor = Color(0xFF1E88E5)),
        LessonItem("shp_triangle", ModuleType.SHAPES, "Triangle", "Three Sharp Corners", "🔺", "A triangle has three straight sides and three pointy corners!", realWorldExample = "House roof, Slice of pizza, Mountain", accentColor = Color(0xFF43A047)),
        LessonItem("shp_rectangle", ModuleType.SHAPES, "Rectangle", "Two Long, Two Short", "🚪", "A rectangle has two long sides and two short sides, like a door!", realWorldExample = "Door, Book, Smartphone", accentColor = Color(0xFFFB8C00)),
        LessonItem("shp_oval", ModuleType.SHAPES, "Oval", "Egg Shaped", "🥚", "An oval is curved like a stretched circle, just like a tasty egg!", realWorldExample = "Egg, Mirror, Watermelon", accentColor = Color(0xFF8E24AA)),
        LessonItem("shp_star", ModuleType.SHAPES, "Star", "Five Pointy Tips", "⭐", "A star has five shining points that twinkle in the evening sky!", realWorldExample = "Night star, Starfish, Magic wand", accentColor = Color(0xFFFDD835)),
        LessonItem("shp_heart", ModuleType.SHAPES, "Heart", "Love and Hugs", "❤️", "A heart is a sweet shape that means love, warmth, and friendly hugs!", realWorldExample = "Valentine card, Strawberry shape", accentColor = Color(0xFFD81B60)),
        LessonItem("shp_diamond", ModuleType.SHAPES, "Diamond", "Flying Kite", "🪁", "A diamond has four tilted corners, flying high in the wind like a kite!", realWorldExample = "Flying kite, Road sign", accentColor = Color(0xFF00ACC1))
    )

    val animalLessons = listOf(
        // Farm
        LessonItem("ani_cow", ModuleType.ANIMALS, "Cow", "Friendly Milk Giver", "🐄", "Moo! Cows live on the farm, eat green grass, and give delicious milk!", soundPrompt = "Moooo!", category = "Farm"),
        LessonItem("ani_horse", ModuleType.ANIMALS, "Horse", "Galloping Runner", "🐎", "Neigh! Horses can run very fast and have gorgeous flowing manes!", soundPrompt = "Neighhh!", category = "Farm"),
        LessonItem("ani_pig", ModuleType.ANIMALS, "Pig", "Curly Tailed Friend", "🐖", "Oink oink! Pigs love rolling in mud to stay cool on warm days!", soundPrompt = "Oink oink!", category = "Farm"),
        LessonItem("ani_chicken", ModuleType.ANIMALS, "Chicken", "Clucking Hen", "🐔", "Cluck cluck! Chickens wake up early and lay fresh farm eggs!", soundPrompt = "Cluck cluck!", category = "Farm"),
        LessonItem("ani_sheep", ModuleType.ANIMALS, "Sheep", "Fluffy Wool Coat", "🐑", "Baa baa! Sheep have warm, fluffy wool that makes cozy sweaters!", soundPrompt = "Baaaa!", category = "Farm"),
        // Jungle
        LessonItem("ani_lion", ModuleType.ANIMALS, "Lion", "King of the Jungle", "🦁", "Roar! Lions are majestic big cats with magnificent furry manes!", soundPrompt = "Rooaaarrr!", category = "Jungle"),
        LessonItem("ani_elephant", ModuleType.ANIMALS, "Elephant", "Gentle Giant", "🐘", "Pawoo! Elephants have great big ears and use their trunk like a hand!", soundPrompt = "Pawoo!", category = "Jungle"),
        LessonItem("ani_monkey", ModuleType.ANIMALS, "Monkey", "Playful Climber", "🐵", "Ooh aah! Monkeys swing acrobatically through leafy trees!", soundPrompt = "Ooh ooh aah aah!", category = "Jungle"),
        LessonItem("ani_tiger", ModuleType.ANIMALS, "Tiger", "Striped Big Cat", "🐯", "Grrr! Tigers have beautiful orange coats with bold black stripes!", soundPrompt = "Grrrrr!", category = "Jungle"),
        LessonItem("ani_giraffe", ModuleType.ANIMALS, "Giraffe", "Tallest Tree Eater", "🦒", "Crunch crunch! Giraffes reach the highest leaves with their long necks!", soundPrompt = "Munch munch!", category = "Jungle"),
        // Ocean
        LessonItem("ani_fish", ModuleType.ANIMALS, "Clownfish", "Little Swimmer", "🐠", "Blub blub! Little fish dart happily around colorful coral reefs!", soundPrompt = "Blub blub!", category = "Ocean"),
        LessonItem("ani_whale", ModuleType.ANIMALS, "Whale", "Ocean Giant", "🐋", "Whoosh! Whales blow tall water fountains high into the fresh air!", soundPrompt = "Whooosh!", category = "Ocean"),
        LessonItem("ani_dolphin", ModuleType.ANIMALS, "Dolphin", "Smart Leaper", "🐬", "Click-whistle! Dolphins love to leap playfully over rolling waves!", soundPrompt = "Eee-eee!", category = "Ocean"),
        LessonItem("ani_octopus", ModuleType.ANIMALS, "Octopus", "Eight Wavy Arms", "🐙", "Pop! An octopus can squirt ink and hide inside small sea shells!", soundPrompt = "Sploosh!", category = "Ocean"),
        // Pets
        LessonItem("ani_dog", ModuleType.ANIMALS, "Dog", "Best Furry Friend", "🐕", "Woof woof! Dogs love chasing balls and getting belly rubs!", soundPrompt = "Woof woof!", category = "Pets"),
        LessonItem("ani_cat", ModuleType.ANIMALS, "Cat", "Purring Cuddler", "🐈", "Meow purr! Cats take cozy naps in sunny spots and love string!", soundPrompt = "Meow purrr!", category = "Pets"),
        LessonItem("ani_rabbit", ModuleType.ANIMALS, "Rabbit", "Bouncy Bunny", "🐇", "Hop hop! Rabbits have long soft ears and munch on crunchy carrots!", soundPrompt = "Thump thump!", category = "Pets"),
        // Birds
        LessonItem("ani_parrot", ModuleType.ANIMALS, "Parrot", "Rainbow Talker", "🦜", "Squawk! Colorful parrots can copy words and fly high in jungles!", soundPrompt = "Squawk!", category = "Birds"),
        LessonItem("ani_owl", ModuleType.ANIMALS, "Owl", "Nighttime Watcher", "🦉", "Hoot hoot! Owls can turn their heads around and see in the dark!", soundPrompt = "Hoot hoot!", category = "Birds"),
        LessonItem("ani_penguin", ModuleType.ANIMALS, "Penguin", "Waddling Polar Bird", "🐧", "Honk! Penguins cannot fly in air, but they fly through cold sea water!", soundPrompt = "Honk honk!", category = "Birds")
    )

    val fruitLessons = listOf(
        LessonItem("frt_apple", ModuleType.FRUITS, "Apple", "Crunchy & Sweet", "🍎", "Apples can be red, green, or yellow! Delicious and healthy!", category = "Sweet"),
        LessonItem("frt_banana", ModuleType.FRUITS, "Banana", "Peel & Smile", "🍌", "Bananas are yellow, curved like a smile, and easy to peel!", category = "Tropical"),
        LessonItem("frt_orange", ModuleType.FRUITS, "Orange", "Juicy Citrus", "🍊", "Oranges are round and packed with juicy, sweet Vitamin C!", category = "Citrus"),
        LessonItem("frt_strawberry", ModuleType.FRUITS, "Strawberry", "Heart-Shaped Berry", "🍓", "Strawberries have tiny seeds on the outside and sweet red juice!", category = "Berry"),
        LessonItem("frt_watermelon", ModuleType.FRUITS, "Watermelon", "Big Summer Slice", "🍉", "Watermelon is green on the outside and sweet pink inside with seeds!", category = "Melon"),
        LessonItem("frt_pineapple", ModuleType.FRUITS, "Pineapple", "Tropical Crown", "🍍", "Pineapples wear a spiky leafy crown and taste sweet and tangy!", category = "Tropical"),
        LessonItem("frt_mango", ModuleType.FRUITS, "Mango", "Golden Paradise", "🥭", "Mango is the juicy king of summer fruits with a smooth golden seed!", category = "Tropical"),
        LessonItem("frt_grapes", ModuleType.FRUITS, "Grapes", "Bite-Sized Bunch", "🍇", "Grapes grow in lovely bunches and can be sweet purple or green!", category = "Berry"),
        LessonItem("frt_pear", ModuleType.FRUITS, "Pear", "Soft & Bell-Shaped", "🍐", "Pears are bell-shaped and soft with gentle, mild honey sweetness!", category = "Sweet"),
        LessonItem("frt_peach", ModuleType.FRUITS, "Peach", "Fuzzy & Sunny", "🍑", "Peaches have soft fuzzy skin and warm sweet juice inside!", category = "Sweet")
    )

    val vehicleLessons = listOf(
        // Road
        LessonItem("veh_car", ModuleType.VEHICLES, "Car", "Everyday Family Ride", "🚗", "Beep beep! Cars drive on roads and have four spinning wheels!", soundPrompt = "Beep beep!", category = "Road"),
        LessonItem("veh_bus", ModuleType.VEHICLES, "Bus", "Big School Bus", "🚌", "Honk honk! The wheels on the bus go round and round all through town!", soundPrompt = "Honk honk!", category = "Road"),
        LessonItem("veh_truck", ModuleType.VEHICLES, "Truck", "Heavy Hauler", "🚚", "Vroom vroom! Giant trucks carry heavy cargo across long highways!", soundPrompt = "Vroom vroom!", category = "Road"),
        LessonItem("veh_bicycle", ModuleType.VEHICLES, "Bicycle", "Pedal & Ring", "🚲", "Ring ring! Pedal with your feet and balance with a safety helmet!", soundPrompt = "Ring ring!", category = "Road"),
        LessonItem("veh_motorcycle", ModuleType.VEHICLES, "Motorcycle", "Two-Wheeled Zoom", "🏍️", "Zoom zoom! Fast and exciting two-wheeled motor ride!", soundPrompt = "Zoom zoom!", category = "Road"),
        // Air
        LessonItem("veh_airplane", ModuleType.VEHICLES, "Airplane", "High Sky Glider", "✈️", "Whoosh! Airplanes fly high above fluffy white clouds in the sky!", soundPrompt = "Whooosh!", category = "Air"),
        LessonItem("veh_helicopter", ModuleType.VEHICLES, "Helicopter", "Spinning Rotor", "🚁", "Chop chop chop! Helicopters can hover in one spot in the air!", soundPrompt = "Chop chop chop!", category = "Air"),
        LessonItem("veh_rocket", ModuleType.VEHICLES, "Rocket", "Space Explorer", "🚀", "3, 2, 1, Blast off! Rockets travel all the way into outer space!", soundPrompt = "Blast off!", category = "Air"),
        // Water
        LessonItem("veh_boat", ModuleType.VEHICLES, "Boat", "Sail on Waves", "⛵", "Splish splash! Wind blows into the sail to move across blue water!", soundPrompt = "Splish splash!", category = "Water"),
        LessonItem("veh_ship", ModuleType.VEHICLES, "Ship", "Big Ocean Liner", "🚢", "Toooot! Giant ships carry passengers across deep oceans!", soundPrompt = "Tooooot!", category = "Water"),
        LessonItem("veh_submarine", ModuleType.VEHICLES, "Submarine", "Undersea Explorer", "🤿", "Ping ping! Submarines dive deep under the ocean with sea turtles!", soundPrompt = "Ping ping!", category = "Water"),
        // Emergency
        LessonItem("veh_firetruck", ModuleType.VEHICLES, "Fire Truck", "Rescue & Siren", "🚒", "Wee-woo! Fire trucks have long ladders and hoses to extinguish fires!", soundPrompt = "Wee-woo wee-woo!", category = "Emergency"),
        LessonItem("veh_ambulance", ModuleType.VEHICLES, "Ambulance", "Hospital Helper", "🚑", "Nee-naw! Ambulances rush quickly to help sick and injured friends!", soundPrompt = "Nee-naw nee-naw!", category = "Emergency"),
        LessonItem("veh_police", ModuleType.VEHICLES, "Police Car", "Safety Watcher", "🚓", "Wee-oo wee-oo! Police officers help keep our neighborhood safe!", soundPrompt = "Wee-oo wee-oo!", category = "Emergency")
    )

    val soundLessons = listOf(
        LessonItem("snd_cow", ModuleType.SOUNDS, "Moo!", "Farm Cow", "🐄", "Can you hear the cow? Moooo!", soundPrompt = "Moooo!"),
        LessonItem("snd_car", ModuleType.SOUNDS, "Beep Beep!", "Car Horn", "🚗", "Beep beep! Watch out, car coming through!", soundPrompt = "Beep beep!"),
        LessonItem("snd_bell", ModuleType.SOUNDS, "Ding Dong!", "Doorbell chime", "🔔", "Ding dong! Who is at the door?", soundPrompt = "Ding dong!"),
        LessonItem("snd_bird", ModuleType.SOUNDS, "Chirp Chirp!", "Singing Bird", "🐦", "Chirp chirp tweet tweet! The happy bird sings in morning light!", soundPrompt = "Chirp chirp!"),
        LessonItem("snd_rain", ModuleType.SOUNDS, "Pitter Patter!", "Gentle Rain", "🌧️", "Pitter patter on the windowpane, gentle soothing raindrops falling!", soundPrompt = "Pitter patter pitter patter!"),
        LessonItem("snd_drum", ModuleType.SOUNDS, "Boom Boom!", "Toy Drum", "🥁", "Boom boom rat-a-tat-tat! Marching to the happy beat!", soundPrompt = "Boom boom boom!"),
        LessonItem("snd_train", ModuleType.SOUNDS, "Choo Choo!", "Steam Train", "🚂", "Choo choo chugga chugga choo choo!", soundPrompt = "Choo choo!"),
        LessonItem("snd_duck", ModuleType.SOUNDS, "Quack Quack!", "Yellow Duckling", "🦆", "Quack quack! Paddling across the pond with mama duck!", soundPrompt = "Quack quack!")
    )

    fun getLessonsForModule(moduleType: ModuleType): List<LessonItem> {
        return when (moduleType) {
            ModuleType.ABC -> abcLessons
            ModuleType.NUMBERS -> numberLessons
            ModuleType.COLORS -> colorLessons
            ModuleType.SHAPES -> shapeLessons
            ModuleType.ANIMALS -> animalLessons
            ModuleType.FRUITS -> fruitLessons
            ModuleType.VEHICLES -> vehicleLessons
            ModuleType.SOUNDS -> soundLessons
        }
    }
}
