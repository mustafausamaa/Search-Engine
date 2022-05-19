import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:search_engine_project/constants.dart';
import 'package:url_launcher/url_launcher.dart';

class LinkViewComponent extends StatelessWidget {
  final url;
  final title;
  final description;
  const LinkViewComponent(
      {Key key,
      this.url = "www.google.com",
      this.title = "no title",
      this.description = "no description"})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: EdgeInsets.symmetric(horizontal: 10, vertical: 10),
      decoration: BoxDecoration(
        color: tertiary,
        borderRadius: BorderRadius.circular(10),
        boxShadow: [
          BoxShadow(
            color: Color.fromARGB(255, 229, 229, 230),
            spreadRadius: 3,
            blurRadius: 5,
            offset: Offset(0, -5),
          )
        ],
      ),
      padding: EdgeInsets.all(10),
      // height: 80,
      child: RichText(
          // maxLines: 2,
          text: TextSpan(children: [
        TextSpan(
          recognizer: TapGestureRecognizer()
            ..onTap = () {
              launch(url.toString());
            },
          text: title.toString(),
          style: TextStyle(
            color: primary,
            decoration: TextDecoration.underline,
            fontWeight: FontWeight.bold,
            fontSize: (20),
          ),
        ),
        TextSpan(
          recognizer: TapGestureRecognizer()
            ..onTap = () {
              launch(url.toString());
            },
          text: description.toString(),
          style: TextStyle(
            height: 1.5,
            color: secondary,
            fontWeight: FontWeight.w500,
            fontSize: (14),
          ),
        )
      ])),
    );
  }
}
