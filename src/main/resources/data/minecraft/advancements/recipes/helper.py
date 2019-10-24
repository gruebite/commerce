#!/usr/bin/env python

import json
import sys

filename = sys.argv[1]
competency = sys.argv[2]

data = None

try:
    print(f"changing {filename}...")
    with open(filename) as json_file:
        data = json.load(json_file)

        to_remove = []
        for k, _ in data["criteria"].items():
            if not k.startswith("has_the_recipe"):
                to_remove.append(k)
        for rem in to_remove:
            del data["criteria"][rem]
            data["requirements"][0].remove(rem)

        competencies = competency.split(",")
        competencies_str = "gained_" + "_".join(competencies)
        data["requirements"][0].append(competencies_str)
        data["criteria"][competencies_str] = {
            "trigger": "commerce:gained_competencies",
            "conditions": {
                "competencies": competencies
            }
        }
except Exception as e:
    print(f"failed to change {filename}: {e}")

with open(filename, "w") as out_file:
    json.dump(data, out_file, indent=2)
