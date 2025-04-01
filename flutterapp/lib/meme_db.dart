import 'package:sqflite/sqflite.dart';
import 'package:path/path.dart';
import 'meme_model.dart';

class MemeDatabase {
  static final MemeDatabase _instance = MemeDatabase._internal();
  factory MemeDatabase() => _instance;
  MemeDatabase._internal();

  static Database? _database;

  Future<Database> get database async {
    if (_database != null) return _database!;
    _database = await _initDatabase();
    return _database!;
  }

  Future<Database> _initDatabase() async {
    String path = join(await getDatabasesPath(), 'memes.db');
    return await openDatabase(
      path,
      version: 2, 
      onCreate: _onCreate,
      onUpgrade: _onUpgrade,
    );
  }

  Future<void> _onCreate(Database db, int version) async {
    await db.execute('''
      CREATE TABLE memes (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        title TEXT,
        description TEXT,
        imageUrl TEXT,
        category TEXT,
        isLiked INTEGER
      )
    ''');
  }

  Future<void> _onUpgrade(Database db, int oldVersion, int newVersion) async {
    if (oldVersion < newVersion) {
      await db.execute("DROP TABLE IF EXISTS memes");
      await _onCreate(db, newVersion);
    }
  }

  Future<void> insertMeme(Meme meme) async {
    final db = await database;
    await db.insert('memes', meme.toMap());
  }

  Future<List<Meme>> getMemes({String? category}) async {
    final db = await database;
    List<Map<String, dynamic>> maps;

    if (category != null && category != "All") {
      maps = await db.query(
        'memes',
        where: 'category = ?',
        whereArgs: [category],
      );
    } else {
      maps = await db.query('memes');
    }

    return List.generate(maps.length, (i) => Meme.fromMap(maps[i]));
  }

  Future<void> updateMeme(Meme meme) async {
    final db = await database;
    await db.update(
      'memes',
      meme.toMap(),
      where: 'id = ?',
      whereArgs: [meme.id],
    );
  }

  Future<void> deleteMeme(int id) async {
    final db = await database;
    await db.delete('memes', where: 'id = ?', whereArgs: [id]);
  }
}
