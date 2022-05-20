import 'package:flutter/material.dart';
import 'package:momentum/momentum.dart';
import 'package:search_engine_project/Documens-controller.dart';
import 'package:search_engine_project/constants.dart';
import 'package:search_engine_project/search-view.dart';
import 'package:splashscreen/splashscreen.dart';

void main() {
  runApp(momentum());
}

Momentum momentum() {
  return Momentum(
    controllers: [DocumentsController()],

    child: MyApp(),
    // persistSave will convert compatible models to json and save it into our storage of choice
  );
}

class MyApp extends StatelessWidget {
  const MyApp({Key key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      color: Color.fromARGB(255, 0, 69, 126),
      debugShowCheckedModeBanner: false,
      title: 'Fora',
      theme: ThemeData(),
      home:
          // NavigationBarViewNew(),
          // FieldProfileView()
          MomentumBuilder(builder: (context, snapshot) {
        return
            // Splash();
            // TimeTableView();
            // Calendar();
            SplashScreen(
          title: Text(
            "Search Engine",
            style: TextStyle(
              color: primary,
              fontWeight: FontWeight.bold,
              fontSize: (30),
            ),
          ),
          useLoader: false,
          seconds: 5,
          navigateAfterSeconds: SearchView(),
          backgroundColor: secondary,
          // imageBackground: AssetImage('lib/assets/Split Screen.png'),
        );
      }),
    );
  }
}
