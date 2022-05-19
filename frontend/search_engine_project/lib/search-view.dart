import 'package:flutter/material.dart';
import 'package:momentum/momentum.dart';
import 'package:search_engine_project/Documens-controller.dart';
import 'package:search_engine_project/Documents-model.dart';
import 'package:search_engine_project/constants.dart';
import 'package:search_engine_project/link-component.dart';
import 'package:search_engine_project/regular_text_input_new.dart';

class SearchView extends StatefulWidget {
  final pageTitle;

  const SearchView({Key key, this.pageTitle}) : super(key: key);
  @override
  _ProfileState createState() => _ProfileState();
}

class _ProfileState extends State<SearchView> {
  void initState() {
    super.initState();
  }

  void search() {
    setState(() {
      ispressed = true;
    });
    Momentum.controller<DocumentsController>(context)
        .getSearchDocuments(controller.text, context);
  }

  bool ispressed = false;
  TextEditingController controller = TextEditingController();
  @override
  Widget build(BuildContext context) {
    double _width = MediaQuery.of(context).size.width;
    double _height = MediaQuery.of(context).size.height;

    final GlobalKey<ScaffoldState> _scaffoldKey =
        new GlobalKey<ScaffoldState>();
    return MomentumBuilder(
        controllers: [DocumentsController],
        builder: (context, snapshot) {
          var model = snapshot<DocumentsModel>();

          List _searchList = [];

          return Scaffold(
              appBar: AppBar(
                title: Text("Search Engine"),
                backgroundColor: secondary,
              ),
              key: _scaffoldKey,
              body: Container(
                decoration: BoxDecoration(
                    gradient: LinearGradient(
                        begin: Alignment.topCenter,
                        end: Alignment.bottomCenter,
                        colors: [
                      Color(0xFFFBFBFE),
                      Color(0xFFECEFFD),
                    ])),
                child: ListView(
                    // physics: NeverScrollableScrollPhysics(),
                    children: [
                      Container(
                        margin: EdgeInsets.only(
                            left: (10 / 390) * _width,
                            right: (10 / 390) * _width,
                            bottom: (15 / 844) * _height),
                        child: Row(
                          children: [
                            Expanded(
                              flex: 3,
                              child: Container(
                                margin: EdgeInsets.only(bottom: 15, right: 5),
                                child: RegularTextInputNew(
                                  controller: controller,
                                  hintText: "Search",
                                  search: true,
                                  labelColor: secondary,
                                  textColor: secondary,
                                  fillColor: Color(0x21214461),
                                  hintTextColor: Color(0x940A1F33),
                                  prefixIconColor: Color(0x94192B37),
                                  errorBorderColor: Color(0xFFFD7542),
                                ),
                              ),
                            ),
                            Expanded(
                              child: Container(
                                child: ElevatedButton(
                                  style: ButtonStyle(
                                      fixedSize:
                                          MaterialStateProperty.all<Size>(
                                              Size(0, 30)),
                                      backgroundColor:
                                          MaterialStateProperty.all<Color>(
                                              primary),
                                      shape: MaterialStateProperty.all<
                                              RoundedRectangleBorder>(
                                          RoundedRectangleBorder(
                                        borderRadius: BorderRadius.circular(5),
                                      ))),
                                  onPressed: () {
                                    search();
                                  },
                                  child: Container(
                                      child: Text(
                                        "Search",
                                        style: TextStyle(color: tertiary),
                                      ),
                                      decoration: BoxDecoration(
                                        color: primary,
                                        borderRadius: BorderRadius.circular(10),
                                      )),
                                ),
                              ),
                            ),
                          ],
                        ),
                      ),
                      (ispressed)
                          ? (!model.isLoading)
                              ? Container(
                                  width: (337 / 390) * _width,
                                  height: (600 / 844) * _height,
                                  child: ListView.builder(
                                      itemCount: model.searchDocuments.length,
                                      //  _searchList.length,
                                      itemBuilder: (context, index) {
                                        return model.searchDocuments[index];
                                      }),
                                )
                              : Container(
                                  margin: EdgeInsets.only(top: 200),
                                  child: Center(
                                    child: CircularProgressIndicator(
                                      color: primary,
                                    ),
                                  ),
                                )
                          : Container(
                              margin: EdgeInsets.only(top: 200),
                              child: Center(
                                child: Column(
                                  children: [
                                    Image.asset(
                                      'lib/search-normal.png',
                                      color: primary,
                                    ),
                                    Container(
                                      margin: EdgeInsets.only(top: 10),
                                      child: Text(
                                        'Start Searching',
                                        style: TextStyle(
                                            color: primary,
                                            fontWeight: FontWeight.bold,
                                            fontSize: 20),
                                      ),
                                    )
                                  ],
                                ),
                              ),
                            ),
                    ]),
              ));
        });
  }
}
